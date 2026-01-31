package com.datasentry.app.presentation.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u001b\u001a\u00020\u001cJ\u0006\u0010\u001d\u001a\u00020\u001cJ\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u000eJ\u0006\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010#0\u000eJ\u000e\u0010$\u001a\u00020\u001cH\u0082@\u00a2\u0006\u0002\u0010%J\u000e\u0010&\u001a\u00020\u001c2\u0006\u0010\'\u001a\u00020\u001fJ\b\u0010(\u001a\u00020\u001cH\u0002J\u0006\u0010)\u001a\u00020\u001cJ\u0006\u0010*\u001a\u00020\u001cR\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2 = {"Lcom/datasentry/app/presentation/dashboard/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/datasentry/app/data/repository/PacketRepository;", "application", "Landroid/app/Application;", "(Lcom/datasentry/app/data/repository/PacketRepository;Landroid/app/Application;)V", "_sessions", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/datasentry/app/data/model/AppSession;", "analysisManager", "Lcom/datasentry/app/security/AnalysisManager;", "packets", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/datasentry/app/data/local/entity/PacketEntity;", "getPackets", "()Lkotlinx/coroutines/flow/StateFlow;", "riskCount", "", "getRiskCount", "sessionAggregator", "Lcom/datasentry/app/analysis/SessionAggregator;", "sessions", "getSessions", "simulator", "Lcom/datasentry/app/domain/model/TrafficSimulator;", "clearLogs", "", "fetchServerStats", "getAnalysisMode", "", "getAnalysisSummary", "Lcom/datasentry/app/security/AnalysisSummary;", "getServerStats", "Lcom/datasentry/app/security/ServerStats;", "refreshSessions", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setAnalysisMode", "mode", "startSessionRefresh", "startSimulation", "stopSimulation", "app_debug"})
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.data.repository.PacketRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final android.app.Application application = null;
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.security.AnalysisManager analysisManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.domain.model.TrafficSimulator simulator = null;
    @org.jetbrains.annotations.NotNull()
    private final com.datasentry.app.analysis.SessionAggregator sessionAggregator = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.datasentry.app.data.local.entity.PacketEntity>> packets = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> riskCount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.datasentry.app.data.model.AppSession>> _sessions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.datasentry.app.data.model.AppSession>> sessions = null;
    
    public DashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.repository.PacketRepository repository, @org.jetbrains.annotations.NotNull()
    android.app.Application application) {
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
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.datasentry.app.data.model.AppSession>> getSessions() {
        return null;
    }
    
    private final void startSessionRefresh() {
    }
    
    private final java.lang.Object refreshSessions(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void startSimulation() {
    }
    
    public final void stopSimulation() {
    }
    
    public final void clearLogs() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getAnalysisMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.datasentry.app.security.ServerStats> getServerStats() {
        return null;
    }
    
    public final void setAnalysisMode(@org.jetbrains.annotations.NotNull()
    java.lang.String mode) {
    }
    
    public final void fetchServerStats() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.datasentry.app.security.AnalysisSummary getAnalysisSummary() {
        return null;
    }
}