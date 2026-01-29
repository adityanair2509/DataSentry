package com.datasentry.app.analysis

import com.datasentry.app.data.local.entity.PacketEntity
import com.datasentry.app.data.model.AppSession
import com.datasentry.app.data.repository.PacketRepository
import kotlinx.coroutines.flow.first

/**
 * Aggregates packets into meaningful app sessions and performs analysis.
 */
class SessionAggregator(private val packetRepository: PacketRepository) {
    
    companion object {
        // Session ends after 2 minutes of inactivity
        private const val SESSION_TIMEOUT_MS = 2 * 60 * 1000L
        
        // Tracker domain patterns
        private val TRACKER_PATTERNS = listOf(
            "analytics",
            "tracking",
            "telemetry",
            "doubleclick",
            "facebook-analytics",
            "graph.facebook",
            "crashlytics",
            "firebase-analytics",
            "adjust.com",
            "apps.mopub",
            "googleadservices",
            "googlesyndication",
            "adservice",
            "adsystem"
        )
    }
    
    /**
     * Get all app sessions from stored packets
     */
    suspend fun getAppSessions(): List<AppSession> {
        val allPackets = packetRepository.allPackets.first()
        
        // Filter out DataSentry's own traffic
        val filteredPackets = allPackets.filter { packet ->
            !packet.packageName.contains("datasentry", ignoreCase = true) &&
            !packet.appName.contains("datasentry", ignoreCase = true) &&
            !packet.appName.equals("DataSentry", ignoreCase = true)
        }
        
        // Group by app
        val byApp = filteredPackets.groupBy { it.appName }
        
        return byApp.flatMap { (appName, packets) ->
            createSessionsFromPackets(appName, packets)
        }.sortedByDescending { it.endTime }
    }
    
    /**
     * Get sessions for a specific app
     */
    suspend fun getSessionsForApp(appName: String): List<AppSession> {
        val packets = packetRepository.allPackets.first().filter { it.appName == appName }
        return createSessionsFromPackets(appName, packets)
    }
    
    /**
     * Create sessions from a list of packets
     */
    private fun createSessionsFromPackets(
        appName: String,
        packets: List<PacketEntity>
    ): List<AppSession> {
        if (packets.isEmpty()) return emptyList()
        
        val sessions = mutableListOf<AppSession>()
        val sortedPackets = packets.sortedBy { it.timestamp }
        
        var currentSessionPackets = mutableListOf<PacketEntity>()
        var sessionStart = sortedPackets.first().timestamp
        
        for (packet in sortedPackets) {
            if (currentSessionPackets.isEmpty()) {
                // Start new session
                currentSessionPackets.add(packet)
                sessionStart = packet.timestamp
            } else {
                val lastPacket = currentSessionPackets.last()
                val gap = packet.timestamp - lastPacket.timestamp
                
                if (gap > SESSION_TIMEOUT_MS) {
                    // Session ended - save it and start new one
                    sessions.add(
                        createSession(
                            appName,
                            sessionStart,
                            lastPacket.timestamp,
                            currentSessionPackets.toList()
                        )
                    )
                    
                    currentSessionPackets.clear()
                    currentSessionPackets.add(packet)
                    sessionStart = packet.timestamp
                } else {
                    // Continue current session
                    currentSessionPackets.add(packet)
                }
            }
        }
        
        // Don't forget last session
        if (currentSessionPackets.isNotEmpty()) {
            sessions.add(
                createSession(
                    appName,
                    sessionStart,
                    currentSessionPackets.last().timestamp,
                    currentSessionPackets
                )
            )
        }
        
        return sessions
    }
    
    /**
     * Create a session object with analysis
     */
    private fun createSession(
        appName: String,
        startTime: Long,
        endTime: Long,
        packets: List<PacketEntity>
    ): AppSession {
        // Extract unique domains
        val domains = packets.map { it.destIp }.filter { it.isNotBlank() }.toSet()
        
        // Identify trackers
        val trackers = domains.filter { isTracker(it) }
        
        // Calculate total bytes
        val totalBytes = packets.sumOf { it.sizeBytes }
        
        // Breakdown by packet size
        val sizeBreakdown = classifyPacketSizes(packets)
        
        // Determine privacy impact
        val privacyImpact = when {
            trackers.size >= 3 -> AppSession.PrivacyImpact.HIGH
            trackers.size >= 1 -> AppSession.PrivacyImpact.MEDIUM
            else -> AppSession.PrivacyImpact.LOW
        }
        
        // Analyze connection pattern
        val connectionPattern = analyzeConnectionPattern(packets)
        
        // Get package name from packets (TrafficStats data) or fall back to heuristic
        val realPackageName = packets.firstOrNull { it.packageName.isNotBlank() }?.packageName ?: ""
        val resolvedPackageName = if (realPackageName.isNotBlank()) {
            realPackageName  // Use real package from TrafficStats
        } else {
            derivePackageName(appName)  // Fall back to heuristic
        }
        
        return AppSession(
            appName = appName,
            packageName = resolvedPackageName,
            startTime = startTime,
            endTime = endTime,
            packets = packets,
            domains = domains,
            trackers = trackers,
            totalBytes = totalBytes,
            packetCount = packets.size,
            packetSizeBreakdown = sizeBreakdown,
            privacyImpact = privacyImpact,
            connectionPattern = connectionPattern
        )
    }
    
    /**
     * Derive package name from app name (enhanced heuristic)
     */
    private fun derivePackageName(appName: String): String {
        val lowerName = appName.lowercase().trim()
        
        // Direct app name mappings
        return when (lowerName) {
            // Google apps - using com.google.android.gms (always installed)
            "google" -> "com.google.android.gms"
            "chrome" -> "com.android.chrome"
            "gmail" -> "com.google.android.gm"
            "maps" -> "com.google.android.apps.maps"
            "youtube" -> "com.google.android.youtube"
            "drive" -> "com.google.android.apps.docs"
            "photos" -> "com.google.android.apps.photos"
            "play store" -> "com.android.vending"
            
            // Social media
            "facebook" -> "com.facebook.katana"
            "instagram" -> "com.instagram.android"
            "whatsapp" -> "com.whatsapp"
            "snapchat" -> "com.snapchat.android"
            "twitter", "x" -> "com.twitter.android"
            "tiktok" -> "com.zhiliaoapp.musically"
            "telegram" -> "org.telegram.messenger"
            "discord" -> "com.discord"
            "reddit" -> "com.reddit.frontpage"
            "linkedin" -> "com.linkedin.android"
            "pinterest" -> "com.pinterest"
            
            // Entertainment
            "spotify" -> "com.spotify.music"
            "netflix" -> "com.netflix.mediaclient"
            "amazon" -> "com.amazon.mShop.android.shopping"
            "prime video" -> "com.amazon.avod.thirdpartyclient"
            
            // Utilities
            "uber" -> "com.ubercab"
            "lyft" -> "me.lyft.android"
            "system" -> "android"
            "unknown" -> ""  // Return empty for unknown apps
            
            // Handle domain-like app names (fallback from DNS detection)
            else -> {
                when {
                    lowerName.contains("google") -> "com.google.android.gms"
                    lowerName.contains("facebook") || lowerName.contains("fbcdn") -> "com.facebook.orca"
                    lowerName.contains("instagram") -> "com.instagram.android"
                    lowerName.contains("whatsapp") -> "com.whatsapp"
                    lowerName.contains("youtube") -> "com.google.android.youtube"
                    lowerName.contains("twitter") -> "com.twitter.android"
                    lowerName.contains("tiktok") -> "com.zhiliaoapp.musically"
                    lowerName.contains("snapchat") -> "com.snapchat.android"
                    lowerName.contains("spotify") -> "com.spotify.music"
                    lowerName.contains("netflix") -> "com.netflix.mediaclient"
                    lowerName.contains("amazon") || lowerName.contains("aws") -> "com.amazon.mShop.android.shopping"
                    lowerName.contains("microsoft") || lowerName.contains("azure") -> "com.microsoft.office.outlook"
                    lowerName.contains("apple") -> "" // Apple apps not on Android
                    lowerName.contains(".com") || lowerName.contains(".net") || lowerName.contains(".org") -> "" // Domain names - can't map
                    else -> "" // Return empty - can't determine package
                }
            }
        }
    }
    
    /**
     * Check if a domain is a tracker
     */
    private fun isTracker(domain: String): Boolean {
        val lowerDomain = domain.lowercase()
        return TRACKER_PATTERNS.any { pattern ->
            lowerDomain.contains(pattern)
        }
    }
    
    /**
     * Classify packets by size into traffic types
     */
    private fun classifyPacketSizes(packets: List<PacketEntity>): Map<String, Int> {
        val classification = packets.groupingBy { packet ->
            when {
                packet.sizeBytes < 200 -> "Text/Chat"
                packet.sizeBytes < 1000 -> "Images/Data"
                else -> "Video/Files"
            }
        }.eachCount()
        
        return classification
    }
    
    /**
     * Analyze connection pattern based on packet timing
     */
    private fun analyzeConnectionPattern(packets: List<PacketEntity>): AppSession.ConnectionPattern {
        if (packets.size < 2) return AppSession.ConnectionPattern.OCCASIONAL
        
        val sortedPackets = packets.sortedBy { it.timestamp }
        val intervals = sortedPackets.zipWithNext { a, b ->
            b.timestamp - a.timestamp
        }
        
        if (intervals.isEmpty()) return AppSession.ConnectionPattern.OCCASIONAL
        
        val avgInterval = intervals.average()
        
        return when {
            avgInterval < 1000 -> AppSession.ConnectionPattern.CONSTANT
            avgInterval < 10000 -> AppSession.ConnectionPattern.FREQUENT
            avgInterval < 60000 -> AppSession.ConnectionPattern.PERIODIC
            else -> AppSession.ConnectionPattern.OCCASIONAL
        }
    }
    
    /**
     * Get session statistics
     */
    fun getSessionStats(sessions: List<AppSession>): SessionStats {
        val totalSessions = sessions.size
        val highRiskSessions = sessions.count { it.privacyImpact == AppSession.PrivacyImpact.HIGH }
        val totalTrackers = sessions.sumOf { it.trackers.size }
        val appsWithTrackers = sessions.count { it.trackers.isNotEmpty() }
        
        return SessionStats(
            totalSessions = totalSessions,
            highRiskSessions = highRiskSessions,
            totalTrackers = totalTrackers,
            appsWithTrackers = appsWithTrackers
        )
    }
    
    data class SessionStats(
        val totalSessions: Int,
        val highRiskSessions: Int,
        val totalTrackers: Int,
        val appsWithTrackers: Int
    )
}
