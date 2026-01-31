package com.datasentry.app.security;

/**
 * Encrypted Log Manager for DataSentry
 * Handles secure storage and retrieval of DNS logs with AES-256 encryption
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0002/0B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0016H\u0002J\u000e\u0010\u0018\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u001c\u0010\u001a\u001a\u00020\u00162\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001eJ\b\u0010\u001f\u001a\u00020\fH\u0002J\b\u0010 \u001a\u00020\u001dH\u0002J\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0086@\u00a2\u0006\u0002\u0010\u0019J\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\b\b\u0002\u0010#\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010$J\u000e\u0010%\u001a\u00020&H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u001c\u0010\'\u001a\u00020\u00162\f\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001eJ\u0016\u0010*\u001a\u00020\u00162\u0006\u0010+\u001a\u00020)H\u0086@\u00a2\u0006\u0002\u0010,J\u0010\u0010-\u001a\u00020\u00162\u0006\u0010.\u001a\u00020\u001dH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u00061"}, d2 = {"Lcom/datasentry/app/security/EncryptedLogManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "dateFormat", "Ljava/text/SimpleDateFormat;", "gson", "Lcom/google/gson/Gson;", "logCounter", "Ljava/util/concurrent/atomic/AtomicLong;", "logsDir", "Ljava/io/File;", "getLogsDir", "()Ljava/io/File;", "logsDir$delegate", "Lkotlin/Lazy;", "maxFileSize", "", "maxLogFiles", "", "checkAndRotateLogFile", "", "cleanupOldLogs", "clearAllLogs", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearTransmittedLogs", "transmittedEntries", "", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentLogFile", "getDeviceId", "getEncryptedLogs", "getRecentEncryptedLogs", "maxEntries", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStorageStats", "Lcom/datasentry/app/security/EncryptedLogManager$LogStorageStats;", "storeLogBatch", "packetEntities", "Lcom/datasentry/app/data/local/entity/PacketEntity;", "storeLogEntry", "packetEntity", "(Lcom/datasentry/app/data/local/entity/PacketEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "writeToCurrentLogFile", "jsonData", "LogEntry", "LogStorageStats", "app_debug"})
public final class EncryptedLogManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicLong logCounter = null;
    @org.jetbrains.annotations.NotNull()
    private final java.text.SimpleDateFormat dateFormat = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy logsDir$delegate = null;
    private final long maxFileSize = 10485760L;
    private final int maxLogFiles = 50;
    
    public EncryptedLogManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final java.io.File getLogsDir() {
        return null;
    }
    
    /**
     * Store a single packet entity as encrypted log entry
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object storeLogEntry(@org.jetbrains.annotations.NotNull()
    com.datasentry.app.data.local.entity.PacketEntity packetEntity, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Clear all existing log files (for testing/debugging)
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearAllLogs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    /**
     * Store multiple packet entities as raw JSON batch (encryption happens at batch level)
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object storeLogBatch(@org.jetbrains.annotations.NotNull()
    java.util.List<com.datasentry.app.data.local.entity.PacketEntity> packetEntities, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Get all raw JSON log entries for transmission
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getEncryptedLogs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
    
    /**
     * Get raw JSON logs for batch transmission (limit to recent logs)
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRecentEncryptedLogs(int maxEntries, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
    
    /**
     * Clear transmitted logs to free up space
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearTransmittedLogs(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> transmittedEntries, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Get storage statistics
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getStorageStats(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.datasentry.app.security.EncryptedLogManager.LogStorageStats> $completion) {
        return null;
    }
    
    /**
     * Write raw JSON data to current log file (properly formatted)
     */
    private final void writeToCurrentLogFile(java.lang.String jsonData) {
    }
    
    /**
     * Get current log file (creates new one if needed)
     */
    private final java.io.File getCurrentLogFile() {
        return null;
    }
    
    /**
     * Check if log file needs rotation and rotate if necessary
     */
    private final void checkAndRotateLogFile() {
    }
    
    /**
     * Clean up old log files to maintain storage limits
     */
    private final void cleanupOldLogs() {
    }
    
    /**
     * Get unique device identifier
     */
    private final java.lang.String getDeviceId() {
        return null;
    }
    
    /**
     * Data class for log entry structure
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\bH\u00c6\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001J\t\u0010\u001b\u001a\u00020\bH\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f\u00a8\u0006\u001c"}, d2 = {"Lcom/datasentry/app/security/EncryptedLogManager$LogEntry;", "", "id", "", "timestamp", "data", "Lcom/datasentry/app/data/local/entity/PacketEntity;", "deviceId", "", "(JJLcom/datasentry/app/data/local/entity/PacketEntity;Ljava/lang/String;)V", "getData", "()Lcom/datasentry/app/data/local/entity/PacketEntity;", "getDeviceId", "()Ljava/lang/String;", "getId", "()J", "getTimestamp", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class LogEntry {
        private final long id = 0L;
        private final long timestamp = 0L;
        @org.jetbrains.annotations.NotNull()
        private final com.datasentry.app.data.local.entity.PacketEntity data = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String deviceId = null;
        
        public LogEntry(long id, long timestamp, @org.jetbrains.annotations.NotNull()
        com.datasentry.app.data.local.entity.PacketEntity data, @org.jetbrains.annotations.NotNull()
        java.lang.String deviceId) {
            super();
        }
        
        public final long getId() {
            return 0L;
        }
        
        public final long getTimestamp() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.datasentry.app.data.local.entity.PacketEntity getData() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDeviceId() {
            return null;
        }
        
        public final long component1() {
            return 0L;
        }
        
        public final long component2() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.datasentry.app.data.local.entity.PacketEntity component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.datasentry.app.security.EncryptedLogManager.LogEntry copy(long id, long timestamp, @org.jetbrains.annotations.NotNull()
        com.datasentry.app.data.local.entity.PacketEntity data, @org.jetbrains.annotations.NotNull()
        java.lang.String deviceId) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    /**
     * Data class for storage statistics
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\f\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00c6\u0003J;\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000bR\u0011\u0010\u0011\u001a\u00020\u00128F\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006!"}, d2 = {"Lcom/datasentry/app/security/EncryptedLogManager$LogStorageStats;", "", "totalFiles", "", "totalSizeBytes", "", "totalEntries", "oldestLogTimestamp", "newestLogTimestamp", "(IJIJJ)V", "getNewestLogTimestamp", "()J", "getOldestLogTimestamp", "getTotalEntries", "()I", "getTotalFiles", "getTotalSizeBytes", "totalSizeMB", "", "getTotalSizeMB", "()D", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class LogStorageStats {
        private final int totalFiles = 0;
        private final long totalSizeBytes = 0L;
        private final int totalEntries = 0;
        private final long oldestLogTimestamp = 0L;
        private final long newestLogTimestamp = 0L;
        
        public LogStorageStats(int totalFiles, long totalSizeBytes, int totalEntries, long oldestLogTimestamp, long newestLogTimestamp) {
            super();
        }
        
        public final int getTotalFiles() {
            return 0;
        }
        
        public final long getTotalSizeBytes() {
            return 0L;
        }
        
        public final int getTotalEntries() {
            return 0;
        }
        
        public final long getOldestLogTimestamp() {
            return 0L;
        }
        
        public final long getNewestLogTimestamp() {
            return 0L;
        }
        
        public final double getTotalSizeMB() {
            return 0.0;
        }
        
        public final int component1() {
            return 0;
        }
        
        public final long component2() {
            return 0L;
        }
        
        public final int component3() {
            return 0;
        }
        
        public final long component4() {
            return 0L;
        }
        
        public final long component5() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.datasentry.app.security.EncryptedLogManager.LogStorageStats copy(int totalFiles, long totalSizeBytes, int totalEntries, long oldestLogTimestamp, long newestLogTimestamp) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}