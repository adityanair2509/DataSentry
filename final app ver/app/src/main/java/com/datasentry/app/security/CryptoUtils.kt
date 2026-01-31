package com.datasentry.app.security

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * AES-256 GCM Encryption Utilities for DataSentry
 * Provides military-grade encryption for log storage and transmission
 */
object CryptoUtils {
    
    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val KEY_SIZE = 256
    private const val GCM_IV_LENGTH = 12
    private const val GCM_TAG_LENGTH = 16
    
    // Shared encryption key (Base64 encoded) - should match server key
    private const val SHARED_KEY_BASE64 = "AWnheBkwOK67kUvXgDFKt6rPmQNFknFwoMrL2Tgo1YI="
    
    // Android Keystore alias for the master key
    private const val KEY_ALIAS = "DataSentryMasterKey"
    private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
    
    /**
     * Get or create secret key for encryption
     * Uses shared key to match server encryption
     */
    private suspend fun getOrCreateSecretKey(context: Context): SecretKey {
        return try {
            // Use shared key to match server
            if (SHARED_KEY_BASE64 != "YOUR_SHARED_KEY_HERE") {
                val keyBytes = Base64.decode(SHARED_KEY_BASE64, Base64.NO_WRAP)
                SecretKeySpec(keyBytes, ALGORITHM)
            } else {
                // Fallback to app-specific key if shared key not set
                generateAppSecretKey(context)
            }
        } catch (e: Exception) {
            Log.e("CryptoUtils", "Failed to use shared key, using fallback", e)
            generateAppSecretKey(context)
        }
    }
    
    /**
     * Generate secret key in Android Keystore
     */
    private fun generateSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            KEYSTORE_PROVIDER
        )
        
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(KEY_SIZE)
            .setRandomizedEncryptionRequired(true)
            .build()
        
        keyGenerator.init(keyGenParameterSpec)
        return keyGenerator.generateKey()
    }
    
    /**
     * Generate fallback app-specific key (stored in EncryptedSharedPreferences)
     */
    private fun generateAppSecretKey(context: Context): SecretKey {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        
        // Generate random key material
        val keyBytes = ByteArray(32)
        SecureRandom().nextBytes(keyBytes)
        
        // Store in EncryptedSharedPreferences
        val encryptedPrefs = EncryptedSharedPreferences.create(
            context,
            "datasentry_crypto_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        
        encryptedPrefs.edit()
            .putString("app_secret_key", Base64.encodeToString(keyBytes, Base64.NO_WRAP))
            .apply()
        
        return SecretKeySpec(keyBytes, ALGORITHM)
    }
    
    /**
     * Encrypt data using AES-256 GCM
     * Returns Base64 encoded string with IV + ciphertext + tag
     */
    suspend fun encrypt(context: Context, data: String): String = withContext(Dispatchers.Default) {
        try {
            val secretKey = getOrCreateSecretKey(context)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            
            // Generate random IV
            val iv = ByteArray(GCM_IV_LENGTH)
            SecureRandom().nextBytes(iv)
            
            val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH * 8, iv)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec)
            
            // Encrypt the data
            val ciphertext = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
            
            // Combine IV + ciphertext and encode as Base64
            val combined = iv + ciphertext
            Base64.encodeToString(combined, Base64.NO_WRAP)
            
        } catch (e: Exception) {
            Log.e("CryptoUtils", "Encryption failed", e)
            throw IOException("Encryption failed: ${e.message}", e)
        }
    }
    
    /**
     * Decrypt data using AES-256 GCM
     * Expects Base64 encoded string with IV + ciphertext + tag
     */
    suspend fun decrypt(context: Context, encryptedData: String): String = withContext(Dispatchers.Default) {
        try {
            val secretKey = getOrCreateSecretKey(context)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            
            // Decode Base64 and separate IV from ciphertext
            val combined = Base64.decode(encryptedData, Base64.NO_WRAP)
            val iv = combined.sliceArray(0 until GCM_IV_LENGTH)
            val ciphertext = combined.sliceArray(GCM_IV_LENGTH until combined.size)
            
            val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH * 8, iv)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec)
            
            // Decrypt the data
            val decryptedBytes = cipher.doFinal(ciphertext)
            String(decryptedBytes, Charsets.UTF_8)
            
        } catch (e: Exception) {
            throw SecurityException("Decryption failed: ${e.message}", e)
        }
    }
    
    /**
     * Encrypt a list of log entries for batch transmission
     */
    suspend fun encryptBatch(context: Context, logEntries: List<String>): String {
        val batchJson = """
            {
                "version": "1.0",
                "timestamp": ${System.currentTimeMillis()},
                "entries": [${logEntries.joinToString(",") { "\"${it.replace("\"", "\\\"")}\"" }}],
                "count": ${logEntries.size}
            }
        """.trimIndent()
        
        return encrypt(context, batchJson)
    }
    
    /**
     * Decrypt batch data received from server
     */
    suspend fun decryptBatch(context: Context, encryptedBatch: String): List<String> {
        val decryptedJson = decrypt(context, encryptedBatch)
        
        // Parse JSON and extract entries
        // In a real implementation, use proper JSON parsing
        return try {
            // Simple extraction - in production, use Gson/Moshi
            val entriesStart = decryptedJson.indexOf("\"entries\":[")
            val entriesEnd = decryptedJson.indexOf("]", entriesStart)
            
            if (entriesStart != -1 && entriesEnd != -1) {
                val entriesSection = decryptedJson.substring(entriesStart + 11, entriesEnd)
                entriesSection.split("\",\"").map { it.replace("\"", "") }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            throw SecurityException("Failed to parse batch data: ${e.message}", e)
        }
    }
    
    /**
     * Generate secure random key for testing purposes
     */
    fun generateTestKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
        keyGenerator.init(KEY_SIZE)
        return keyGenerator.generateKey()
    }
    
    /**
     * Validate encryption/decryption is working correctly
     */
    suspend fun validateEncryption(context: Context): Boolean {
        return try {
            val testData = "DataSentry encryption test ${System.currentTimeMillis()}"
            val encrypted = encrypt(context, testData)
            val decrypted = decrypt(context, encrypted)
            testData == decrypted
        } catch (e: Exception) {
            false
        }
    }
}
