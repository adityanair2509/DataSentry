package com.datasentry.app.inspector

data class PacketMetadata(
    val protocol: String,
    val sourceIp: String,
    val destIp: String,
    val sourcePort: Int,
    val destPort: Int,
    val length: Int,
    val timestamp: Long = System.currentTimeMillis()
)
