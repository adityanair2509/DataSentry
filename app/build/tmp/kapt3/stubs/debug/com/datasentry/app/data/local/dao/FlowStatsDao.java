package com.datasentry.app.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ$\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u001b\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\"J\u0014\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0$H\'J\u001c\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0$2\u0006\u0010\u0006\u001a\u00020\u0007H\'J\u0016\u0010&\u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\"J\u001e\u0010\'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0017H\u00a7@\u00a2\u0006\u0002\u0010)J\u0016\u0010*\u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\"\u00a8\u0006+"}, d2 = {"Lcom/datasentry/app/data/local/dao/FlowStatsDao;", "", "deleteAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteByPackage", "packageName", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOlderThan", "timestamp", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "", "Lcom/datasentry/app/data/model/FlowStats;", "getByPackage", "getByTimeRange", "startTime", "endTime", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByTrafficType", "trafficType", "Lcom/datasentry/app/data/model/TrafficType;", "(Lcom/datasentry/app/data/model/TrafficType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDataUsageByApp", "Lcom/datasentry/app/data/local/dao/AppDataUsage;", "getDataUsageByPackage", "getDataUsageByTrafficType", "Lcom/datasentry/app/data/local/dao/TrafficTypeUsage;", "getMostRecentFlows", "getTotalDataUsage", "insert", "flowStats", "(Lcom/datasentry/app/data/model/FlowStats;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAll", "Lkotlinx/coroutines/flow/Flow;", "observeByPackage", "update", "updateTrafficType", "id", "(JLcom/datasentry/app/data/model/TrafficType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "upsert", "app_debug"})
@androidx.room.Dao()
public abstract interface FlowStatsDao {
    
    @androidx.room.Query(value = "SELECT * FROM flow_stats ORDER BY lastSeen DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.datasentry.app.data.model.FlowStats>> observeAll();
    
    @androidx.room.Query(value = "SELECT * FROM flow_stats WHERE packageName = :packageName ORDER BY lastSeen DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.datasentry.app.data.model.FlowStats>> observeByPackage(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName);
    
    @androidx.room.Query(value = "SELECT * FROM flow_stats ORDER BY lastSeen DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.FlowStats>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM flow_stats WHERE packageName = :packageName ORDER BY lastSeen DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByPackage(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.FlowStats>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM flow_stats WHERE trafficType = :trafficType ORDER BY lastSeen DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByTrafficType(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.TrafficType trafficType, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.FlowStats>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM flow_stats WHERE firstSeen >= :startTime AND firstSeen <= :endTime ORDER BY lastSeen DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByTimeRange(long startTime, long endTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.FlowStats>> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(totalBytes) FROM flow_stats")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTotalDataUsage(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(totalBytes) FROM flow_stats WHERE packageName = :packageName")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDataUsageByPackage(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT trafficType, SUM(totalBytes) as totalBytes FROM flow_stats GROUP BY trafficType")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDataUsageByTrafficType(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.local.dao.TrafficTypeUsage>> $completion);
    
    @androidx.room.Query(value = "SELECT packageName, SUM(totalBytes) as totalBytes FROM flow_stats WHERE packageName IS NOT NULL GROUP BY packageName")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDataUsageByApp(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.local.dao.AppDataUsage>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM flow_stats ORDER BY lastSeen DESC LIMIT 10")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMostRecentFlows(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.FlowStats>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.FlowStats flowStats, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Upsert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object upsert(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.FlowStats flowStats, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.FlowStats flowStats, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE flow_stats SET trafficType = :trafficType WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateTrafficType(long id, @org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.TrafficType trafficType, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM flow_stats")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM flow_stats WHERE packageName = :packageName")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteByPackage(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM flow_stats WHERE firstSeen < :timestamp")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteOlderThan(long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}