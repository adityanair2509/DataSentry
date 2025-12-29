package com.datasentry.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u0006H\u0096@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0096@\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\t\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u001c\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0015\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010\u0017J$\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u001bJ\u001c\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u001d\u001a\u00020\u001eH\u0096@\u00a2\u0006\u0002\u0010\u001fJ\u000e\u0010 \u001a\u00020!H\u0096@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\"\u001a\u00020!2\u0006\u0010\u0015\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010\u0017J\u0010\u0010#\u001a\u0004\u0018\u00010\u0012H\u0096@\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010%\u001a\u00020!H\u0096@\u00a2\u0006\u0002\u0010&J\u0014\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110(H\u0016J\u001c\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110(2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110(2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0016\u0010+\u001a\u00020\u000e2\u0006\u0010,\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010-J\u0016\u0010.\u001a\u00020\u00062\u0006\u0010,\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010-R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/datasentry/app/data/repository/SuspiciousEventRepositoryImpl;", "Lcom/datasentry/app/data/repository/SuspiciousEventRepository;", "suspiciousEventDao", "Lcom/datasentry/app/data/local/dao/SuspiciousEventDao;", "(Lcom/datasentry/app/data/local/dao/SuspiciousEventDao;)V", "deleteAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteByPackage", "packageName", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOlderThan", "timestamp", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "", "Lcom/datasentry/app/data/model/SuspiciousEvent;", "getByPackage", "getByRiskLevel", "riskLevel", "Lcom/datasentry/app/data/model/RiskLevel;", "(Lcom/datasentry/app/data/model/RiskLevel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByTimeRange", "startTime", "endTime", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByTrafficType", "trafficType", "Lcom/datasentry/app/data/model/TrafficType;", "(Lcom/datasentry/app/data/model/TrafficType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCount", "", "getCountByRiskLevel", "getLatest", "getRecent", "limit", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAll", "Lkotlinx/coroutines/flow/Flow;", "observeByPackage", "observeByRiskLevel", "save", "event", "(Lcom/datasentry/app/data/model/SuspiciousEvent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "app_debug"})
public final class SuspiciousEventRepositoryImpl implements com.datasentry.app.data.repository.SuspiciousEventRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.data.local.dao.SuspiciousEventDao suspiciousEventDao = null;
    
    public SuspiciousEventRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.local.dao.SuspiciousEventDao suspiciousEventDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> observeAll() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> observeByPackage(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> observeByRiskLevel(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.RiskLevel riskLevel) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getByPackage(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getByRiskLevel(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.RiskLevel riskLevel, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getByTrafficType(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.TrafficType trafficType, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getByTimeRange(long startTime, long endTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getCountByRiskLevel(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.RiskLevel riskLevel, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getLatest(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.datasentry.app.data.model.SuspiciousEvent> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getRecent(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object save(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.SuspiciousEvent event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.SuspiciousEvent event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteByPackage(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteOlderThan(long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}