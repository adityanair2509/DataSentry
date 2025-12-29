package com.datasentry.app.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ$\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u000e\u0010\u0015\u001a\u00020\u0016H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u0014\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u001cH\'J\u001c\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u001c2\u0006\u0010\u0006\u001a\u00020\u0007H\'J\u0016\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u001a\u00a8\u0006\u001f"}, d2 = {"Lcom/datasentry/app/data/local/dao/AppUsageDao;", "", "deleteAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteByPackage", "packageName", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOlderThan", "timestamp", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "", "Lcom/datasentry/app/data/model/AppUsage;", "getByPackage", "getByTimeRange", "startTime", "endTime", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUniquePackageCount", "", "getUniquePackages", "insert", "appUsage", "(Lcom/datasentry/app/data/model/AppUsage;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAll", "Lkotlinx/coroutines/flow/Flow;", "observeByPackage", "update", "app_debug"})
@androidx.room.Dao()
public abstract interface AppUsageDao {
    
    @androidx.room.Query(value = "SELECT * FROM app_usage ORDER BY startTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.datasentry.app.data.model.AppUsage>> observeAll();
    
    @androidx.room.Query(value = "SELECT * FROM app_usage WHERE packageName = :packageName ORDER BY startTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.datasentry.app.data.model.AppUsage>> observeByPackage(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName);
    
    @androidx.room.Query(value = "SELECT * FROM app_usage ORDER BY startTime DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.AppUsage>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM app_usage WHERE packageName = :packageName ORDER BY startTime DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByPackage(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.AppUsage>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM app_usage WHERE startTime >= :startTime AND startTime <= :endTime ORDER BY startTime DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByTimeRange(long startTime, long endTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.datasentry.app.data.model.AppUsage>> $completion);
    
    @androidx.room.Query(value = "SELECT DISTINCT packageName FROM app_usage WHERE packageName IS NOT NULL")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUniquePackages(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(DISTINCT packageName) FROM app_usage WHERE packageName IS NOT NULL")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUniquePackageCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.AppUsage appUsage, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.AppUsage appUsage, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM app_usage")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM app_usage WHERE packageName = :packageName")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteByPackage(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM app_usage WHERE startTime < :timestamp")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteOlderThan(long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}