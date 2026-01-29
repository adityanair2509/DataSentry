package com.datasentry.app.data.remote

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

/**
 * Simple DNS Packet Parser for extracting Hostnames from UDP payloads.
 * Based on RFC 1035.
 */
data class DnsPacket(
    val id: Short,
    val flags: Short,
    val questions: List<DnsQuestion>
) {
    companion object {
        fun parse(data: ByteArray): DnsPacket? {
            try {
                val buffer = ByteBuffer.wrap(data)
                
                // Header (12 bytes)
                val id = buffer.short
                val flags = buffer.short
                val qdCount = buffer.short // Question Count
                val anCount = buffer.short // Answer Count
                val nsCount = buffer.short // Authority Count
                val arCount = buffer.short // Additional Count

             
                val questions = ArrayList<DnsQuestion>()
                for (i in 0 until qdCount) {
                    val name = parseName(buffer)
                    val type = buffer.short
                    val clazz = buffer.short
                    questions.add(DnsQuestion(name, type, clazz))
                }

                return DnsPacket(id, flags, questions)
            } catch (e: Exception) {
                // Malformed packet
                return null
            }
        }

        private fun parseName(buffer: ByteBuffer): String {
            val sb = StringBuilder()
            var length = buffer.get().toInt()
            
            while (length != 0) {
                if ((length and 0xC0) == 0xC0) {
                    // Compression pointer (jump to offset)
                    // We don't need to support full decompression for simple requests
                    // Just skip it or return what we have
                    buffer.get() // Skip 2nd byte of pointer
                    return sb.toString().trimEnd('.')
                }
                
                val label = ByteArray(length)
                buffer.get(label)
                sb.append(String(label, StandardCharsets.UTF_8))
                sb.append(".")
                
                length = buffer.get().toInt()
            }
            
            return sb.toString().trimEnd('.')
        }
    }
}

data class DnsQuestion(
    val name: String,
    val type: Short,
    val clazz: Short
)
