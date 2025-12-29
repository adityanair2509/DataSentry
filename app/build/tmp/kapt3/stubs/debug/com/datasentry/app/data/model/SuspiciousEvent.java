package com.datasentry.app.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b$\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001Bc\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0007\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0011J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010$\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\t\u0010%\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010\'\u001a\u00020\tH\u00c6\u0003J\t\u0010(\u001a\u00020\u000bH\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0007H\u00c6\u0003J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003Jx\u0010-\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010.J\u0013\u0010/\u001a\u0002002\b\u00101\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00102\u001a\u00020\u0005H\u00d6\u0001J\t\u00103\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0011\u0010\r\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001aR\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0018R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0018R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"\u00a8\u00064"}, d2 = {"Lcom/datasentry/app/data/model/SuspiciousEvent;", "", "id", "", "appUid", "", "packageName", "", "trafficType", "Lcom/datasentry/app/data/model/TrafficType;", "riskLevel", "Lcom/datasentry/app/data/model/RiskLevel;", "totalBytes", "reason", "timestamp", "protocol", "destinationPort", "(JILjava/lang/String;Lcom/datasentry/app/data/model/TrafficType;Lcom/datasentry/app/data/model/RiskLevel;JLjava/lang/String;JLjava/lang/String;Ljava/lang/Integer;)V", "getAppUid", "()I", "getDestinationPort", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getId", "()J", "getPackageName", "()Ljava/lang/String;", "getProtocol", "getReason", "getRiskLevel", "()Lcom/datasentry/app/data/model/RiskLevel;", "getTimestamp", "getTotalBytes", "getTrafficType", "()Lcom/datasentry/app/data/model/TrafficType;", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JILjava/lang/String;Lcom/datasentry/app/data/model/TrafficType;Lcom/datasentry/app/data/model/RiskLevel;JLjava/lang/String;JLjava/lang/String;Ljava/lang/Integer;)Lcom/datasentry/app/data/model/SuspiciousEvent;", "equals", "", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "suspicious_events")
public final class SuspiciousEvent {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final int appUid = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String packageName = null;
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.data.model.TrafficType trafficType = null;
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.data.model.RiskLevel riskLevel = null;
    private final long totalBytes = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String reason = null;
    private final long timestamp = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String protocol = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer destinationPort = null;
    
    public SuspiciousEvent(long id, int appUid, @org.jetbrains.annotations.Nullable()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.TrafficType trafficType, @org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.RiskLevel riskLevel, long totalBytes, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, long timestamp, @org.jetbrains.annotations.Nullable()
    java.lang.String protocol, @org.jetbrains.annotations.Nullable()
    java.lang.Integer destinationPort) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final int getAppUid() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPackageName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.datasentry.app.data.model.TrafficType getTrafficType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.datasentry.app.data.model.RiskLevel getRiskLevel() {
        return null;
    }
    
    public final long getTotalBytes() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getReason() {
        return null;
    }
    
    public final long getTimestamp() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getProtocol() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getDestinationPort() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component10() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.datasentry.app.data.model.TrafficType component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.datasentry.app.data.model.RiskLevel component5() {
        return null;
    }
    
    public final long component6() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    public final long component8() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.datasentry.app.data.model.SuspiciousEvent copy(long id, int appUid, @org.jetbrains.annotations.Nullable()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.TrafficType trafficType, @org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.RiskLevel riskLevel, long totalBytes, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, long timestamp, @org.jetbrains.annotations.Nullable()
    java.lang.String protocol, @org.jetbrains.annotations.Nullable()
    java.lang.Integer destinationPort) {
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