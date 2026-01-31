package com.datasentry.app.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\f\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\b\u0010\u000e\u001a\u00020\bH\u0002J\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0086@\u00a2\u0006\u0002\u0010\u000bJ.\u0010\u0011\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00100\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0015J(\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0015J\u0016\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\bH\u0082@\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\bJ\u000e\u0010\u001c\u001a\u00020\rH\u0082@\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/datasentry/app/network/SecureAnalyticsClient;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "encryptedLogManager", "Lcom/datasentry/app/security/EncryptedLogManager;", "serverUrl", "", "clearOldLogs", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearServerData", "", "getDeviceId", "getServerStats", "", "sendEncryptedBatch", "", "packets", "Lcom/datasentry/app/data/local/entity/PacketEntity;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendEncryptedData", "sendToServer", "encryptedData", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setServerUrl", "url", "testConnection", "updateServerUrl", "newUrl", "Companion", "app_debug"})
public final class SecureAnalyticsClient {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.security.EncryptedLogManager encryptedLogManager = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String API_KEY = "datasentry-secure-api-key-2024";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DEFAULT_SERVER_URL = "http://192.168.1.100:5000";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String serverUrl = "http://192.168.1.100:5000";
    @org.jetbrains.annotations.NotNull()
    public static final com.datasentry.app.network.SecureAnalyticsClient.Companion Companion = null;
    
    public SecureAnalyticsClient(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void updateServerUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String newUrl) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendEncryptedData(@org.jetbrains.annotations.NotNull()
    java.util.List<com.datasentry.app.data.local.entity.PacketEntity> packets, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.Map<java.lang.String, ? extends java.lang.Object>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendEncryptedBatch(@org.jetbrains.annotations.NotNull()
    java.util.List<com.datasentry.app.data.local.entity.PacketEntity> packets, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<? extends java.util.Map<java.lang.String, ? extends java.lang.Object>>> $completion) {
        return null;
    }
    
    private final java.lang.Object sendToServer(java.lang.String encryptedData, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getServerStats(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.Map<java.lang.String, ? extends java.lang.Object>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearServerData(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.String getDeviceId() {
        return null;
    }
    
    private final java.lang.Object testConnection(kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    public final void setServerUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearOldLogs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/datasentry/app/network/SecureAnalyticsClient$Companion;", "", "()V", "API_KEY", "", "DEFAULT_SERVER_URL", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}