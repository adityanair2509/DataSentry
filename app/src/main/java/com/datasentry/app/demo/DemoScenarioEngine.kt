package com.datasentry.app.demo

/**
 * Demo Scenario Engine for simulating "Advanced Traffic Analysis" results.
 * 
 * This engine provides hardcoded analysis results for specific apps to enable
 * a convincing demo video without requiring the real backend.
 * 
 * Usage:
 * ```kotlin
 * val scenario = DemoScenarioEngine.getScenario("com.google.android.youtube")
 * if (scenario != null) {
 *     // Display the fake analysis result
 * }
 * ```
 */
object DemoScenarioEngine {

    private val scenarios: Map<String, AnalysisResult> = mapOf(
        // YouTube - Video streaming
        "com.google.android.youtube" to AnalysisResult(
            packageName = "com.google.android.youtube",
            trafficType = "VIDEO_STREAM_4K",
            server = "Google Video Cache (Mountain View, US)",
            riskScore = 15,
            insight = "High bandwidth detected. Consistent UDP stream. Verified Certificate."
        ),
        
        // Chrome - Web browsing
        "com.android.chrome" to AnalysisResult(
            packageName = "com.android.chrome",
            trafficType = "WEB_NAVIGATION",
            server = "Cloudflare CDN (Singapore)",
            riskScore = 10,
            insight = "Standard HTTPS browsing. No tracker injection detected."
        ),
        
        // System/Android background
        "android" to AnalysisResult(
            packageName = "android",
            trafficType = "BACKGROUND_TELEMETRY",
            server = "Google Services (Unknown)",
            riskScore = 5,
            insight = "System background sync. Low volume heartbeat traffic."
        )
    )

    /**
     * Returns a pre-defined analysis scenario for the given package name.
     * 
     * @param packageName The package name of the app (e.g., "com.google.android.youtube")
     * @return The hardcoded AnalysisResult, or null if no scenario exists for this app.
     */
    fun getScenario(packageName: String): AnalysisResult? {
        return scenarios[packageName]
    }

    /**
     * Returns a scenario based on destination IP address patterns.
     * Useful when package name is not available but IP can be inspected.
     * 
     * @param destIp The destination IP address from the packet
     * @return The matched AnalysisResult, or null if no pattern matches.
     */
    fun getScenarioByIp(destIp: String): AnalysisResult? {
        return when {
            // Google Video (YouTube) - common IP ranges
            destIp.startsWith("172.217.") || 
            destIp.startsWith("142.250.") ||
            destIp.startsWith("216.58.") ||
            destIp.contains("googlevideo") -> scenarios["com.google.android.youtube"]
            
            // Snapchat (AWS ranges - simplified)
            destIp.startsWith("52.") ||
            destIp.startsWith("54.") -> scenarios["com.snapchat.android"]
            
            // Cloudflare (Chrome CDN)
            destIp.startsWith("104.16.") ||
            destIp.startsWith("104.17.") ||
            destIp.startsWith("1.1.1.") -> scenarios["com.android.chrome"]
            
            // Google services (system)
            destIp.startsWith("142.251.") ||
            destIp.startsWith("74.125.") -> scenarios["android"]
            
            else -> null
        }
    }

    /**
     * Get all available demo scenarios (for UI display/testing).
     */
    fun getAllScenarios(): List<AnalysisResult> = scenarios.values.toList()
}
