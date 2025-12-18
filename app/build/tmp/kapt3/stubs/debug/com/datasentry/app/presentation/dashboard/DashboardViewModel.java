package com.datasentry.app.presentation.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0011J\u0006\u0010\u0013\u001a\u00020\u0011R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/datasentry/app/presentation/dashboard/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/datasentry/app/data/repository/PacketRepository;", "(Lcom/datasentry/app/data/repository/PacketRepository;)V", "packets", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/datasentry/app/data/local/entity/PacketEntity;", "getPackets", "()Lkotlinx/coroutines/flow/StateFlow;", "riskCount", "", "getRiskCount", "simulator", "Lcom/datasentry/app/domain/model/TrafficSimulator;", "clearLogs", "", "startSimulation", "stopSimulation", "app_debug"})
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.data.repository.PacketRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.domain.model.TrafficSimulator simulator = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.datasentry.app.data.local.entity.PacketEntity>> packets = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> riskCount = null;
    
    public DashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.repository.PacketRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.datasentry.app.data.local.entity.PacketEntity>> getPackets() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getRiskCount() {
        return null;
    }
    
    public final void startSimulation() {
    }
    
    public final void stopSimulation() {
    }
    
    public final void clearLogs() {
    }
}