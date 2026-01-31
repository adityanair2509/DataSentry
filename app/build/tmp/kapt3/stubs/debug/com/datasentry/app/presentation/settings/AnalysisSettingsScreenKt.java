package com.datasentry.app.presentation.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a>\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\b\u0010\u0010\u001a\u00020\u0001H\u0007\u001a\u0010\u0010\u0011\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0007\u001a\b\u0010\u0012\u001a\u00020\u0001H\u0007\u001a*\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0019\u001a$\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\n2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\fH\u0007\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001d"}, d2 = {"AnalysisModeCard", "", "mode", "Lcom/datasentry/app/presentation/settings/AnalysisMode;", "isSelected", "", "onSelected", "Lkotlin/Function0;", "AnalysisSettingsScreen", "currentMode", "", "onModeChanged", "Lkotlin/Function1;", "serverStats", "Lcom/datasentry/app/security/ServerStats;", "onNavigateBack", "InfoCard", "ServerStatsSection", "ServerUrlSection", "StatItem", "label", "value", "color", "Landroidx/compose/ui/graphics/Color;", "StatItem-mxwnekA", "(Ljava/lang/String;Ljava/lang/String;J)V", "VirusTotalApiKeySection", "apiKey", "onKeyChanged", "app_debug"})
public final class AnalysisSettingsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void AnalysisSettingsScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String currentMode, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onModeChanged, @org.jetbrains.annotations.Nullable()
    com.datasentry.app.security.ServerStats serverStats, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AnalysisModeCard(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.presentation.settings.AnalysisMode mode, boolean isSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void VirusTotalApiKeySection(@org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onKeyChanged) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ServerStatsSection(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.security.ServerStats serverStats) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ServerUrlSection() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void InfoCard() {
    }
}