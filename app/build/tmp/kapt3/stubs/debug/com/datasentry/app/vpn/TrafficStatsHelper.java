package com.datasentry.app.vpn;

/**
 * Uses Android's TrafficStats API to get real per-app data usage.
 *
 * This provides actual bytes sent/received per app, and can identify
 * which app is most likely generating traffic at any given moment.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \u001d2\u00020\u0001:\u0002\u001c\u001dB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eJ\u000e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\tJ\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u0012J\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u0012J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0015\u001a\u00020\u000eJ\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\f0\u00122\b\b\u0002\u0010\u0017\u001a\u00020\u0007J\u001a\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0019\u001a\u00020\u0007J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0015\u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R&\u0010\u0005\u001a\u001a\u0012\u0004\u0012\u00020\u0007\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/datasentry/app/vpn/TrafficStatsHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "previousReadings", "", "", "Lkotlin/Pair;", "", "recentDeltas", "findMostActiveApp", "Lcom/datasentry/app/vpn/TrafficStatsHelper$AppTrafficInfo;", "domainHint", "", "formatBytes", "bytes", "getActiveApps", "", "getAllAppsTraffic", "getAppTraffic", "packageName", "getTopApps", "limit", "getTrafficDelta", "uid", "isPackageInstalled", "", "AppTrafficInfo", "Companion", "app_debug"})
public final class TrafficStatsHelper {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "TrafficStatsHelper";
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.String, java.lang.String> KNOWN_PACKAGES = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.Integer, kotlin.Pair<java.lang.Long, java.lang.Long>> previousReadings = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.Integer, java.lang.Long> recentDeltas = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.datasentry.app.vpn.TrafficStatsHelper.Companion Companion = null;
    
    public TrafficStatsHelper(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Get traffic stats for all installed apps.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.datasentry.app.vpn.TrafficStatsHelper.AppTrafficInfo> getAllAppsTraffic() {
        return null;
    }
    
    /**
     * Get traffic delta for a specific UID.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlin.Pair<java.lang.Long, java.lang.Long> getTrafficDelta(int uid) {
        return null;
    }
    
    /**
     * Get traffic for a specific package name.
     */
    @org.jetbrains.annotations.Nullable()
    public final com.datasentry.app.vpn.TrafficStatsHelper.AppTrafficInfo getAppTraffic(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
        return null;
    }
    
    /**
     * Get apps that have had recent traffic.
     * Key method for correlation-based app detection.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.datasentry.app.vpn.TrafficStatsHelper.AppTrafficInfo> getActiveApps() {
        return null;
    }
    
    /**
     * Find the most likely app that generated a network request.
     * Uses domain hints for better accuracy.
     */
    @org.jetbrains.annotations.Nullable()
    public final com.datasentry.app.vpn.TrafficStatsHelper.AppTrafficInfo findMostActiveApp(@org.jetbrains.annotations.Nullable()
    java.lang.String domainHint) {
        return null;
    }
    
    /**
     * Get top N apps by data usage.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.datasentry.app.vpn.TrafficStatsHelper.AppTrafficInfo> getTopApps(int limit) {
        return null;
    }
    
    /**
     * Format bytes to human readable.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatBytes(long bytes) {
        return null;
    }
    
    /**
     * Check if a package is installed.
     */
    public final boolean isPackageInstalled(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001b\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\bH\u00c6\u0003JO\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020\u0006H\u00d6\u0001J\t\u0010#\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u000b\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006$"}, d2 = {"Lcom/datasentry/app/vpn/TrafficStatsHelper$AppTrafficInfo;", "", "packageName", "", "appName", "uid", "", "rxBytes", "", "txBytes", "totalBytes", "recentDelta", "(Ljava/lang/String;Ljava/lang/String;IJJJJ)V", "getAppName", "()Ljava/lang/String;", "getPackageName", "getRecentDelta", "()J", "getRxBytes", "getTotalBytes", "getTxBytes", "getUid", "()I", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
    public static final class AppTrafficInfo {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String packageName = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String appName = null;
        private final int uid = 0;
        private final long rxBytes = 0L;
        private final long txBytes = 0L;
        private final long totalBytes = 0L;
        private final long recentDelta = 0L;
        
        public AppTrafficInfo(@org.jetbrains.annotations.NotNull()
        java.lang.String packageName, @org.jetbrains.annotations.NotNull()
        java.lang.String appName, int uid, long rxBytes, long txBytes, long totalBytes, long recentDelta) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getPackageName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getAppName() {
            return null;
        }
        
        public final int getUid() {
            return 0;
        }
        
        public final long getRxBytes() {
            return 0L;
        }
        
        public final long getTxBytes() {
            return 0L;
        }
        
        public final long getTotalBytes() {
            return 0L;
        }
        
        public final long getRecentDelta() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        public final int component3() {
            return 0;
        }
        
        public final long component4() {
            return 0L;
        }
        
        public final long component5() {
            return 0L;
        }
        
        public final long component6() {
            return 0L;
        }
        
        public final long component7() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.datasentry.app.vpn.TrafficStatsHelper.AppTrafficInfo copy(@org.jetbrains.annotations.NotNull()
        java.lang.String packageName, @org.jetbrains.annotations.NotNull()
        java.lang.String appName, int uid, long rxBytes, long txBytes, long totalBytes, long recentDelta) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/datasentry/app/vpn/TrafficStatsHelper$Companion;", "", "()V", "KNOWN_PACKAGES", "", "", "getKNOWN_PACKAGES", "()Ljava/util/Map;", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, java.lang.String> getKNOWN_PACKAGES() {
            return null;
        }
    }
}