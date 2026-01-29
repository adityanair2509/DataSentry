package com.datasentry.app.presentation.sessions;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\b\u0010\u0006\u001a\u00020\u0001H\u0007\u001a\u0018\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003H\u0007\u001a \u0010\n\u001a\u00020\u00012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007\u001a\u0010\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\rH\u0007\u001a*\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0019\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001a"}, d2 = {"DomainItem", "", "domain", "", "isTracker", "", "EmptySessionsView", "MetricItem", "label", "value", "SessionBasedDashboard", "sessions", "", "Lcom/datasentry/app/data/model/AppSession;", "modifier", "Landroidx/compose/ui/Modifier;", "SessionCard", "session", "TrafficBreakdownItem", "type", "percentage", "", "color", "Landroidx/compose/ui/graphics/Color;", "TrafficBreakdownItem-mxwnekA", "(Ljava/lang/String;IJ)V", "app_debug"})
public final class SessionDashboardScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void SessionBasedDashboard(@org.jetbrains.annotations.NotNull()
    java.util.List<com.datasentry.app.data.model.AppSession> sessions, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SessionCard(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.model.AppSession session) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void MetricItem(@org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DomainItem(@org.jetbrains.annotations.NotNull()
    java.lang.String domain, boolean isTracker) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmptySessionsView() {
    }
}