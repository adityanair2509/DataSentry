package com.datasentry.app.security;

/**
 * AES-256 GCM Encryption Utilities for DataSentry
 * Provides military-grade encryption for log storage and transmission
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0004H\u0086@\u00a2\u0006\u0002\u0010\u0011J$\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u00132\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0004H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0004H\u0086@\u00a2\u0006\u0002\u0010\u0011J$\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00040\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u001c\u001a\u00020\u001bH\u0002J\u0006\u0010\u001d\u001a\u00020\u001bJ\u0016\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020!2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u001fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/datasentry/app/security/CryptoUtils;", "", "()V", "ALGORITHM", "", "GCM_IV_LENGTH", "", "GCM_TAG_LENGTH", "KEYSTORE_PROVIDER", "KEY_ALIAS", "KEY_SIZE", "SHARED_KEY_BASE64", "TRANSFORMATION", "decrypt", "context", "Landroid/content/Context;", "encryptedData", "(Landroid/content/Context;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "decryptBatch", "", "encryptedBatch", "encrypt", "data", "encryptBatch", "logEntries", "(Landroid/content/Context;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateAppSecretKey", "Ljavax/crypto/SecretKey;", "generateSecretKey", "generateTestKey", "getOrCreateSecretKey", "(Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "validateEncryption", "", "app_debug"})
public final class CryptoUtils {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ALGORITHM = "AES";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int KEY_SIZE = 256;
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String SHARED_KEY_BASE64 = "AWnheBkwOK67kUvXgDFKt6rPmQNFknFwoMrL2Tgo1YI=";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_ALIAS = "DataSentryMasterKey";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEYSTORE_PROVIDER = "AndroidKeyStore";
    @org.jetbrains.annotations.NotNull()
    public static final com.datasentry.app.security.CryptoUtils INSTANCE = null;
    
    private CryptoUtils() {
        super();
    }
    
    /**
     * Get or create secret key for encryption
     * Uses shared key to match server encryption
     */
    private final java.lang.Object getOrCreateSecretKey(android.content.Context context, kotlin.coroutines.Continuation<? super javax.crypto.SecretKey> $completion) {
        return null;
    }
    
    /**
     * Generate secret key in Android Keystore
     */
    private final javax.crypto.SecretKey generateSecretKey() {
        return null;
    }
    
    /**
     * Generate fallback app-specific key (stored in EncryptedSharedPreferences)
     */
    private final javax.crypto.SecretKey generateAppSecretKey(android.content.Context context) {
        return null;
    }
    
    /**
     * Encrypt data using AES-256 GCM
     * Returns Base64 encoded string with IV + ciphertext + tag
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object encrypt(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String data, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Decrypt data using AES-256 GCM
     * Expects Base64 encoded string with IV + ciphertext + tag
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object decrypt(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String encryptedData, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Encrypt a list of log entries for batch transmission
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object encryptBatch(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> logEntries, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Decrypt batch data received from server
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object decryptBatch(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String encryptedBatch, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
    
    /**
     * Generate secure random key for testing purposes
     */
    @org.jetbrains.annotations.NotNull()
    public final javax.crypto.SecretKey generateTestKey() {
        return null;
    }
    
    /**
     * Validate encryption/decryption is working correctly
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object validateEncryption(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}