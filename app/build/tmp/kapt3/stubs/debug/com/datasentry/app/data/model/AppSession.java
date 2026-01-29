package com.datasentry.app.data.model;

/**
 * Represents a usage session for an app.
 * A session groups multiple packets together based on temporal proximity.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b#\n\u0002\u0010\u000b\n\u0002\b\f\b\u0086\b\u0018\u00002\u00020\u0001:\u0002DEB\u0085\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000f0\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\u0002\u0010\u0017J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\u0015\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000f0\u0012H\u00c6\u0003J\t\u0010.\u001a\u00020\u0014H\u00c6\u0003J\t\u0010/\u001a\u00020\u0016H\u00c6\u0003J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u0006H\u00c6\u0003J\t\u00102\u001a\u00020\u0006H\u00c6\u0003J\u000f\u00103\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\u000f\u00104\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u00c6\u0003J\u000f\u00105\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00c6\u0003J\t\u00106\u001a\u00020\u000fH\u00c6\u0003J\t\u00107\u001a\u00020\u000fH\u00c6\u0003J\u009f\u0001\u00108\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\u0014\b\u0002\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000f0\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u00c6\u0001J\u0013\u00109\u001a\u00020:2\b\u0010;\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u0010<\u001a\u00020\u0003J\u0006\u0010=\u001a\u00020\u0006J\u0006\u0010>\u001a\u00020\u0003J\u0006\u0010?\u001a\u00020\u0003J\u0006\u0010@\u001a\u00020\u0003J\u0006\u0010A\u001a\u00020\u0003J\t\u0010B\u001a\u00020\u000fH\u00d6\u0001J\t\u0010C\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0019R\u0011\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u001d\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000f0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001fR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\"R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010&\u00a8\u0006F"}, d2 = {"Lcom/datasentry/app/data/model/AppSession;", "", "appName", "", "packageName", "startTime", "", "endTime", "packets", "", "Lcom/datasentry/app/data/local/entity/PacketEntity;", "domains", "", "trackers", "totalBytes", "", "packetCount", "packetSizeBreakdown", "", "privacyImpact", "Lcom/datasentry/app/data/model/AppSession$PrivacyImpact;", "connectionPattern", "Lcom/datasentry/app/data/model/AppSession$ConnectionPattern;", "(Ljava/lang/String;Ljava/lang/String;JJLjava/util/List;Ljava/util/Set;Ljava/util/List;IILjava/util/Map;Lcom/datasentry/app/data/model/AppSession$PrivacyImpact;Lcom/datasentry/app/data/model/AppSession$ConnectionPattern;)V", "getAppName", "()Ljava/lang/String;", "getConnectionPattern", "()Lcom/datasentry/app/data/model/AppSession$ConnectionPattern;", "getDomains", "()Ljava/util/Set;", "getEndTime", "()J", "getPackageName", "getPacketCount", "()I", "getPacketSizeBreakdown", "()Ljava/util/Map;", "getPackets", "()Ljava/util/List;", "getPrivacyImpact", "()Lcom/datasentry/app/data/model/AppSession$PrivacyImpact;", "getStartTime", "getTotalBytes", "getTrackers", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "getDominantTrafficType", "getDuration", "getFormattedDuration", "getInsightMessage", "getPrivacyRatingText", "getTimeAgo", "hashCode", "toString", "ConnectionPattern", "PrivacyImpact", "app_debug"})
public final class AppSession {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String appName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String packageName = null;
    private final long startTime = 0L;
    private final long endTime = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.datasentry.app.data.local.entity.PacketEntity> packets = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> domains = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> trackers = null;
    private final int totalBytes = 0;
    private final int packetCount = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Integer> packetSizeBreakdown = null;
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.data.model.AppSession.PrivacyImpact privacyImpact = null;
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.data.model.AppSession.ConnectionPattern connectionPattern = null;
    
    public AppSession(@org.jetbrains.annotations.NotNull()
    java.lang.String appName, @org.jetbrains.annotations.NotNull()
    java.lang.String packageName, long startTime, long endTime, @org.jetbrains.annotations.NotNull()
    java.util.List<com.datasentry.app.data.local.entity.PacketEntity> packets, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> domains, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> trackers, int totalBytes, int packetCount, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Integer> packetSizeBreakdown, @org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.AppSession.PrivacyImpact privacyImpact, @org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.AppSession.ConnectionPattern connectionPattern) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAppName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPackageName() {
        return null;
    }
    
    public final long getStartTime() {
        return 0L;
    }
    
    public final long getEndTime() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.datasentry.app.data.local.entity.PacketEntity> getPackets() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> getDomains() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getTrackers() {
        return null;
    }
    
    public final int getTotalBytes() {
        return 0;
    }
    
    public final int getPacketCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Integer> getPacketSizeBreakdown() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.datasentry.app.data.model.AppSession.PrivacyImpact getPrivacyImpact() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.datasentry.app.data.model.AppSession.ConnectionPattern getConnectionPattern() {
        return null;
    }
    
    /**
     * Get duration of session in milliseconds
     */
    public final long getDuration() {
        return 0L;
    }
    
    /**
     * Get formatted duration string
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedDuration() {
        return null;
    }
    
    /**
     * Get time ago string
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTimeAgo() {
        return null;
    }
    
    /**
     * Get dominant traffic type
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDominantTrafficType() {
        return null;
    }
    
    /**
     * Get user-friendly privacy rating text
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPrivacyRatingText() {
        return null;
    }
    
    /**
     * Get plain-English insight message for collapsed card
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getInsightMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Integer> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.datasentry.app.data.model.AppSession.PrivacyImpact component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.datasentry.app.data.model.AppSession.ConnectionPattern component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final long component3() {
        return 0L;
    }
    
    public final long component4() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.datasentry.app.data.local.entity.PacketEntity> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component7() {
        return null;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.datasentry.app.data.model.AppSession copy(@org.jetbrains.annotations.NotNull()
    java.lang.String appName, @org.jetbrains.annotations.NotNull()
    java.lang.String packageName, long startTime, long endTime, @org.jetbrains.annotations.NotNull()
    java.util.List<com.datasentry.app.data.local.entity.PacketEntity> packets, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> domains, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> trackers, int totalBytes, int packetCount, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Integer> packetSizeBreakdown, @org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.AppSession.PrivacyImpact privacyImpact, @org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.AppSession.ConnectionPattern connectionPattern) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2 = {"Lcom/datasentry/app/data/model/AppSession$ConnectionPattern;", "", "(Ljava/lang/String;I)V", "toDisplayString", "", "CONSTANT", "FREQUENT", "PERIODIC", "OCCASIONAL", "app_debug"})
    public static enum ConnectionPattern {
        /*public static final*/ CONSTANT /* = new CONSTANT() */,
        /*public static final*/ FREQUENT /* = new FREQUENT() */,
        /*public static final*/ PERIODIC /* = new PERIODIC() */,
        /*public static final*/ OCCASIONAL /* = new OCCASIONAL() */;
        
        ConnectionPattern() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String toDisplayString() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.datasentry.app.data.model.AppSession.ConnectionPattern> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/datasentry/app/data/model/AppSession$PrivacyImpact;", "", "(Ljava/lang/String;I)V", "toDisplayString", "", "LOW", "MEDIUM", "HIGH", "app_debug"})
    public static enum PrivacyImpact {
        /*public static final*/ LOW /* = new LOW() */,
        /*public static final*/ MEDIUM /* = new MEDIUM() */,
        /*public static final*/ HIGH /* = new HIGH() */;
        
        PrivacyImpact() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String toDisplayString() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.datasentry.app.data.model.AppSession.PrivacyImpact> getEntries() {
            return null;
        }
    }
}