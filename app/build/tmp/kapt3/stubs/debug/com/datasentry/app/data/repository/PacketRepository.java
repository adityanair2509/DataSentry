package com.datasentry.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0013R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/datasentry/app/data/repository/PacketRepository;", "", "packetDao", "Lcom/datasentry/app/data/local/dao/PacketDao;", "(Lcom/datasentry/app/data/local/dao/PacketDao;)V", "allPackets", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/datasentry/app/data/local/entity/PacketEntity;", "getAllPackets", "()Lkotlinx/coroutines/flow/Flow;", "riskCount", "", "getRiskCount", "clearLogs", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logPacket", "packet", "(Lcom/datasentry/app/data/local/entity/PacketEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class PacketRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.data.local.dao.PacketDao packetDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.datasentry.app.data.local.entity.PacketEntity>> allPackets = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> riskCount = null;
    
    public PacketRepository(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.local.dao.PacketDao packetDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.datasentry.app.data.local.entity.PacketEntity>> getAllPackets() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getRiskCount() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object logPacket(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.local.entity.PacketEntity packet, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearLogs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}