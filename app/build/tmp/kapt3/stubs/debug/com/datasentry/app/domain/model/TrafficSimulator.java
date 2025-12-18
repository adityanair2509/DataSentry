package com.datasentry.app.domain.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0002J\u000e\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0006\u0010\f\u001a\u00020\nR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/datasentry/app/domain/model/TrafficSimulator;", "", "repository", "Lcom/datasentry/app/data/repository/PacketRepository;", "(Lcom/datasentry/app/data/repository/PacketRepository;)V", "isSimulating", "", "generateRandomPacket", "Lcom/datasentry/app/data/local/entity/PacketEntity;", "startSimulation", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "stopSimulation", "app_debug"})
public final class TrafficSimulator {
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.data.repository.PacketRepository repository = null;
    private boolean isSimulating = false;
    
    public TrafficSimulator(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.repository.PacketRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object startSimulation(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void stopSimulation() {
    }
    
    private final com.datasentry.app.data.local.entity.PacketEntity generateRandomPacket() {
        return null;
    }
}