package com.datasentry.app.demo

/**
 * Represents a fake "Advanced Traffic Analysis" result for demo purposes.
 */
data class AnalysisResult(
    val packageName: String,
    val trafficType: String,
    val server: String,
    val riskScore: Int,
    val insight: String
) {
    /**
     * Returns a human-readable risk label based on the score.
     */
    val riskLabel: String
        get() = when {
            riskScore <= 20 -> "Safe"
            riskScore <= 50 -> "Moderate"
            riskScore <= 75 -> "High"
            else -> "Critical"
        }
}
