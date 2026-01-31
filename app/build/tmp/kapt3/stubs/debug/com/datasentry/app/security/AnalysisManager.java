package com.datasentry.app.security;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\"\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u0016\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u000e\u0010\u001e\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010 J\u000e\u0010!\u001a\u00020\"H\u0086@\u00a2\u0006\u0002\u0010 J\u0006\u0010\f\u001a\u00020\u0007J\u0006\u0010#\u001a\u00020$J\u0010\u0010%\u001a\u00020\u001f2\u0006\u0010&\u001a\u00020\u0007H\u0002J\"\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0082@\u00a2\u0006\u0002\u0010\u001aJ\u000e\u0010(\u001a\u00020\"2\u0006\u0010)\u001a\u00020\u0007R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/datasentry/app/security/AnalysisManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_analysisMode", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_serverStats", "Lcom/datasentry/app/security/ServerStats;", "analysisMode", "Lkotlinx/coroutines/flow/StateFlow;", "getAnalysisMode", "()Lkotlinx/coroutines/flow/StateFlow;", "secureAnalyticsClient", "Lcom/datasentry/app/network/SecureAnalyticsClient;", "serverStats", "getServerStats", "sharedPreferences", "Landroid/content/SharedPreferences;", "virusTotalAnalyzer", "Lcom/datasentry/app/security/VirusTotalAnalyzer;", "analyzeBatch", "", "Lcom/datasentry/app/data/local/entity/PacketEntity;", "packets", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "analyzePacket", "packet", "(Lcom/datasentry/app/data/local/entity/PacketEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearServerData", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchServerStats", "", "getAnalysisSummary", "Lcom/datasentry/app/security/AnalysisSummary;", "isSuspiciousDomain", "domain", "sendBatchToServer", "setAnalysisMode", "mode", "app_debug"})
public final class AnalysisManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences sharedPreferences = null;
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.network.SecureAnalyticsClient secureAnalyticsClient = null;
    @org.jetbrains.annotations.Nullable()
    private com.datasentry.app.security.VirusTotalAnalyzer virusTotalAnalyzer;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _analysisMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> analysisMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.datasentry.app.security.ServerStats> _serverStats = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.datasentry.app.security.ServerStats> serverStats = null;
    
    public AnalysisManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getAnalysisMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.datasentry.app.security.ServerStats> getServerStats() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAnalysisMode() {
        return null;
    }
    
    public final void setAnalysisMode(@org.jetbrains.annotations.NotNull()
    java.lang.String mode) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object analyzePacket(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.local.entity.PacketEntity packet, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.datasentry.app.data.local.entity.PacketEntity> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object analyzeBatch(@org.jetbrains.annotations.NotNull()
    java.util.List<com.datasentry.app.data.local.entity.PacketEntity> packets, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.local.entity.PacketEntity>> $completion) {
        return null;
    }
    
    private final java.lang.Object sendBatchToServer(java.util.List<com.datasentry.app.data.local.entity.PacketEntity> packets, kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.local.entity.PacketEntity>> $completion) {
        return null;
    }
    
    private final boolean isSuspiciousDomain(java.lang.String domain) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object fetchServerStats(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearServerData(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.datasentry.app.security.AnalysisSummary getAnalysisSummary() {
        return null;
    }
}