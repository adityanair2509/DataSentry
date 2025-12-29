package com.datasentry.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a6@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a6@\u00a2\u0006\u0002\u0010\fJ\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0006\u001a\u00020\u0007H\u00a6@\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u00a6@\u00a2\u0006\u0002\u0010\u0014J$\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u000bH\u00a6@\u00a2\u0006\u0002\u0010\u0018J\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u001a\u001a\u00020\u001bH\u00a6@\u00a2\u0006\u0002\u0010\u001cJ\u000e\u0010\u001d\u001a\u00020\u001eH\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u0012\u001a\u00020\u0013H\u00a6@\u00a2\u0006\u0002\u0010\u0014J\u0010\u0010 \u001a\u0004\u0018\u00010\u000fH\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\"\u001a\u00020\u001eH\u00a6@\u00a2\u0006\u0002\u0010#J\u0014\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0%H&J\u001c\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0%2\u0006\u0010\u0006\u001a\u00020\u0007H&J\u001c\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0%2\u0006\u0010\u0012\u001a\u00020\u0013H&J\u0016\u0010(\u001a\u00020\u000b2\u0006\u0010)\u001a\u00020\u000fH\u00a6@\u00a2\u0006\u0002\u0010*J\u0016\u0010+\u001a\u00020\u00032\u0006\u0010)\u001a\u00020\u000fH\u00a6@\u00a2\u0006\u0002\u0010*\u00a8\u0006,"}, d2 = {"Lcom/datasentry/app/data/repository/SuspiciousEventRepository;", "", "deleteAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteByPackage", "packageName", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOlderThan", "timestamp", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "", "Lcom/datasentry/app/data/model/SuspiciousEvent;", "getByPackage", "getByRiskLevel", "riskLevel", "Lcom/datasentry/app/data/model/RiskLevel;", "(Lcom/datasentry/app/data/model/RiskLevel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByTimeRange", "startTime", "endTime", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByTrafficType", "trafficType", "Lcom/datasentry/app/data/model/TrafficType;", "(Lcom/datasentry/app/data/model/TrafficType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCount", "", "getCountByRiskLevel", "getLatest", "getRecent", "limit", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAll", "Lkotlinx/coroutines/flow/Flow;", "observeByPackage", "observeByRiskLevel", "save", "event", "(Lcom/datasentry/app/data/model/SuspiciousEvent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "app_debug"})
public abstract interface SuspiciousEventRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> observeAll();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> observeByPackage(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> observeByRiskLevel(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.RiskLevel riskLevel);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByPackage(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByRiskLevel(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.RiskLevel riskLevel, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByTrafficType(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.TrafficType trafficType, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByTimeRange(long startTime, long endTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCountByRiskLevel(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.RiskLevel riskLevel, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLatest(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.datasentry.app.data.model.SuspiciousEvent> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRecent(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.SuspiciousEvent>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object save(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.SuspiciousEvent event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.SuspiciousEvent event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteByPackage(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteOlderThan(long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}