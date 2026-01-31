package com.datasentry.app.security;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005H\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0005H\u0002J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u0005H\u0002J\u0010\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0005H\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\n\u001a\u00020\u0005H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/datasentry/app/security/VirusTotalAnalyzer;", "", "context", "Landroid/content/Context;", "apiKey", "", "(Landroid/content/Context;Ljava/lang/String;)V", "baseUrl", "analyzeDomain", "Lcom/datasentry/app/security/VirusTotalResult;", "domain", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "analyzePacket", "Lcom/datasentry/app/data/local/entity/PacketEntity;", "packet", "(Lcom/datasentry/app/data/local/entity/PacketEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractDomain", "destIp", "getDomainReport", "parseDomainReport", "response", "submitDomainForAnalysis", "", "app_debug"})
public final class VirusTotalAnalyzer {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String apiKey = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String baseUrl = "https://www.virustotal.com/vtapi/v2";
    
    public VirusTotalAnalyzer(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object analyzeDomain(@org.jetbrains.annotations.NotNull()
    java.lang.String domain, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.datasentry.app.security.VirusTotalResult> $completion) {
        return null;
    }
    
    private final com.datasentry.app.security.VirusTotalResult getDomainReport(java.lang.String domain) {
        return null;
    }
    
    private final void submitDomainForAnalysis(java.lang.String domain) {
    }
    
    private final com.datasentry.app.security.VirusTotalResult parseDomainReport(java.lang.String response) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object analyzePacket(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.local.entity.PacketEntity packet, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.datasentry.app.data.local.entity.PacketEntity> $completion) {
        return null;
    }
    
    private final java.lang.String extractDomain(java.lang.String destIp) {
        return null;
    }
}