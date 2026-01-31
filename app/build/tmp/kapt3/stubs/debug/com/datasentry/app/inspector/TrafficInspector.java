package com.datasentry.app.inspector;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ\u0006\u0010\f\u001a\u00020\rJ\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\nJ\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\nJ\u0016\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/datasentry/app/inspector/TrafficInspector;", "", "()V", "flows", "", "", "Lcom/datasentry/app/data/model/FlowStats;", "clear", "", "detectSuspiciousTraffic", "", "Lcom/datasentry/app/data/model/SuspiciousEvent;", "getFlowSummary", "", "getFlows", "getTrafficSummaries", "Lcom/datasentry/app/inspector/TrafficSummary;", "inspect", "packet", "", "appUid", "app_debug"})
public final class TrafficInspector {
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.Integer, com.datasentry.app.data.model.FlowStats> flows = null;
    
    public TrafficInspector() {
        super();
    }
    
    public final void inspect(@org.jetbrains.annotations.NotNull()
    byte[] packet, int appUid) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFlowSummary() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.datasentry.app.inspector.TrafficSummary> getTrafficSummaries() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.datasentry.app.data.model.SuspiciousEvent> detectSuspiciousTraffic() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.datasentry.app.data.model.FlowStats> getFlows() {
        return null;
    }
    
    public final void clear() {
    }
}