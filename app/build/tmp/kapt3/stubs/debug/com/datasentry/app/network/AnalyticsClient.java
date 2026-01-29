package com.datasentry.app.network;

/**
 * Client to send DNS data to Linux analysis server
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\u0018\u0000 \u001a2\u00020\u0001:\u0003\u0019\u001a\u001bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u0012H\u0002J\b\u0010\u0015\u001a\u00020\u0012H\u0002J\b\u0010\u0016\u001a\u00020\u0012H\u0002J\b\u0010\u0017\u001a\u00020\u000fH\u0002J\u0006\u0010\u0018\u001a\u00020\u000fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/datasentry/app/network/AnalyticsClient;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "client", "Lokhttp3/OkHttpClient;", "gson", "Lcom/google/gson/Gson;", "pendingPackets", "", "Lcom/datasentry/app/data/local/entity/PacketEntity;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "addDnsQuery", "", "packet", "extractDomainFromIp", "", "ip", "getApiKey", "getAppVersion", "getDeviceId", "sendBatch", "startPeriodicSending", "BatchDnsData", "Companion", "DnsQuery", "app_debug"})
public final class AnalyticsClient {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AnalyticsClient";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String SERVER_URL = "https://thomasine-hyperdulic-gilda.ngrok-free.dev/api/dns-data";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String API_KEY = "datasentry-quick-api-key-12345";
    private static final int BATCH_SIZE = 10;
    private static final long SEND_INTERVAL_MS = 5000L;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient client = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.datasentry.app.data.local.entity.PacketEntity> pendingPackets = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.datasentry.app.network.AnalyticsClient.Companion Companion = null;
    
    public AnalyticsClient(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Add DNS query to batch queue
     */
    public final void addDnsQuery(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.local.entity.PacketEntity packet) {
    }
    
    /**
     * Start periodic batch sending
     */
    public final void startPeriodicSending() {
    }
    
    private final void sendBatch() {
    }
    
    private final java.lang.String extractDomainFromIp(java.lang.String ip) {
        return null;
    }
    
    private final java.lang.String getDeviceId() {
        return null;
    }
    
    private final java.lang.String getAppVersion() {
        return null;
    }
    
    private final java.lang.String getApiKey() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u0015\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\bH\u00c6\u0003J9\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\bH\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001a"}, d2 = {"Lcom/datasentry/app/network/AnalyticsClient$BatchDnsData;", "", "deviceId", "", "queries", "", "Lcom/datasentry/app/network/AnalyticsClient$DnsQuery;", "metadata", "", "(Ljava/lang/String;Ljava/util/List;Ljava/util/Map;)V", "getDeviceId", "()Ljava/lang/String;", "getMetadata", "()Ljava/util/Map;", "getQueries", "()Ljava/util/List;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class BatchDnsData {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String deviceId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.datasentry.app.network.AnalyticsClient.DnsQuery> queries = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.Map<java.lang.String, java.lang.Object> metadata = null;
        
        public BatchDnsData(@org.jetbrains.annotations.NotNull()
        java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
        java.util.List<com.datasentry.app.network.AnalyticsClient.DnsQuery> queries, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, ? extends java.lang.Object> metadata) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDeviceId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.datasentry.app.network.AnalyticsClient.DnsQuery> getQueries() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, java.lang.Object> getMetadata() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.datasentry.app.network.AnalyticsClient.DnsQuery> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, java.lang.Object> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.datasentry.app.network.AnalyticsClient.BatchDnsData copy(@org.jetbrains.annotations.NotNull()
        java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
        java.util.List<com.datasentry.app.network.AnalyticsClient.DnsQuery> queries, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, ? extends java.lang.Object> metadata) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/datasentry/app/network/AnalyticsClient$Companion;", "", "()V", "API_KEY", "", "BATCH_SIZE", "", "SEND_INTERVAL_MS", "", "SERVER_URL", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001a\b\u0086\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0005H\u00c6\u0003J\t\u0010 \u001a\u00020\u000bH\u00c6\u0003J\t\u0010!\u001a\u00020\rH\u00c6\u0003JY\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001J\u0013\u0010#\u001a\u00020\r2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020\u000bH\u00d6\u0001J\t\u0010&\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0013R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\'"}, d2 = {"Lcom/datasentry/app/network/AnalyticsClient$DnsQuery;", "", "timestamp", "", "domain", "", "appName", "sourceIp", "destIp", "protocol", "sizeBytes", "", "isRisk", "", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IZ)V", "getAppName", "()Ljava/lang/String;", "getDestIp", "getDomain", "()Z", "getProtocol", "getSizeBytes", "()I", "getSourceIp", "getTimestamp", "()J", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
    public static final class DnsQuery {
        private final long timestamp = 0L;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String domain = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String appName = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String sourceIp = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String destIp = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String protocol = null;
        private final int sizeBytes = 0;
        private final boolean isRisk = false;
        
        public DnsQuery(long timestamp, @org.jetbrains.annotations.NotNull()
        java.lang.String domain, @org.jetbrains.annotations.NotNull()
        java.lang.String appName, @org.jetbrains.annotations.NotNull()
        java.lang.String sourceIp, @org.jetbrains.annotations.NotNull()
        java.lang.String destIp, @org.jetbrains.annotations.NotNull()
        java.lang.String protocol, int sizeBytes, boolean isRisk) {
            super();
        }
        
        public final long getTimestamp() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDomain() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getAppName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSourceIp() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDestIp() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getProtocol() {
            return null;
        }
        
        public final int getSizeBytes() {
            return 0;
        }
        
        public final boolean isRisk() {
            return false;
        }
        
        public final long component1() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component6() {
            return null;
        }
        
        public final int component7() {
            return 0;
        }
        
        public final boolean component8() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.datasentry.app.network.AnalyticsClient.DnsQuery copy(long timestamp, @org.jetbrains.annotations.NotNull()
        java.lang.String domain, @org.jetbrains.annotations.NotNull()
        java.lang.String appName, @org.jetbrains.annotations.NotNull()
        java.lang.String sourceIp, @org.jetbrains.annotations.NotNull()
        java.lang.String destIp, @org.jetbrains.annotations.NotNull()
        java.lang.String protocol, int sizeBytes, boolean isRisk) {
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
}