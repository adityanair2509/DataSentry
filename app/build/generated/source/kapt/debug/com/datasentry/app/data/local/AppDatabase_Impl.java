package com.datasentry.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.datasentry.app.data.local.dao.AppUsageDao;
import com.datasentry.app.data.local.dao.AppUsageDao_Impl;
import com.datasentry.app.data.local.dao.FlowStatsDao;
import com.datasentry.app.data.local.dao.FlowStatsDao_Impl;
import com.datasentry.app.data.local.dao.PacketDao;
import com.datasentry.app.data.local.dao.PacketDao_Impl;
import com.datasentry.app.data.local.dao.SuspiciousEventDao;
import com.datasentry.app.data.local.dao.SuspiciousEventDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile PacketDao _packetDao;

  private volatile AppUsageDao _appUsageDao;

  private volatile FlowStatsDao _flowStatsDao;

  private volatile SuspiciousEventDao _suspiciousEventDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `packets` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `sourceIp` TEXT NOT NULL, `destIp` TEXT NOT NULL, `protocol` TEXT NOT NULL, `sizeBytes` INTEGER NOT NULL, `appName` TEXT NOT NULL, `contentType` TEXT NOT NULL, `isRisk` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `app_usage` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `packageName` TEXT NOT NULL, `appName` TEXT, `startTime` INTEGER NOT NULL, `endTime` INTEGER, `duration` INTEGER NOT NULL, `isForeground` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `flow_stats` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `appUid` INTEGER NOT NULL, `packageName` TEXT, `firstSeen` INTEGER NOT NULL, `lastSeen` INTEGER NOT NULL, `packetCount` INTEGER NOT NULL, `totalBytes` INTEGER NOT NULL, `trafficType` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `suspicious_events` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `appUid` INTEGER NOT NULL, `packageName` TEXT, `trafficType` TEXT NOT NULL, `riskLevel` TEXT NOT NULL, `totalBytes` INTEGER NOT NULL, `reason` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `protocol` TEXT, `destinationPort` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a212f338be8d727a9fafd6d4e59f0989')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `packets`");
        db.execSQL("DROP TABLE IF EXISTS `app_usage`");
        db.execSQL("DROP TABLE IF EXISTS `flow_stats`");
        db.execSQL("DROP TABLE IF EXISTS `suspicious_events`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsPackets = new HashMap<String, TableInfo.Column>(9);
        _columnsPackets.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackets.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackets.put("sourceIp", new TableInfo.Column("sourceIp", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackets.put("destIp", new TableInfo.Column("destIp", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackets.put("protocol", new TableInfo.Column("protocol", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackets.put("sizeBytes", new TableInfo.Column("sizeBytes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackets.put("appName", new TableInfo.Column("appName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackets.put("contentType", new TableInfo.Column("contentType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackets.put("isRisk", new TableInfo.Column("isRisk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPackets = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPackets = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPackets = new TableInfo("packets", _columnsPackets, _foreignKeysPackets, _indicesPackets);
        final TableInfo _existingPackets = TableInfo.read(db, "packets");
        if (!_infoPackets.equals(_existingPackets)) {
          return new RoomOpenHelper.ValidationResult(false, "packets(com.datasentry.app.data.local.entity.PacketEntity).\n"
                  + " Expected:\n" + _infoPackets + "\n"
                  + " Found:\n" + _existingPackets);
        }
        final HashMap<String, TableInfo.Column> _columnsAppUsage = new HashMap<String, TableInfo.Column>(7);
        _columnsAppUsage.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppUsage.put("packageName", new TableInfo.Column("packageName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppUsage.put("appName", new TableInfo.Column("appName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppUsage.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppUsage.put("endTime", new TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppUsage.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppUsage.put("isForeground", new TableInfo.Column("isForeground", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAppUsage = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAppUsage = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAppUsage = new TableInfo("app_usage", _columnsAppUsage, _foreignKeysAppUsage, _indicesAppUsage);
        final TableInfo _existingAppUsage = TableInfo.read(db, "app_usage");
        if (!_infoAppUsage.equals(_existingAppUsage)) {
          return new RoomOpenHelper.ValidationResult(false, "app_usage(com.datasentry.app.data.model.AppUsage).\n"
                  + " Expected:\n" + _infoAppUsage + "\n"
                  + " Found:\n" + _existingAppUsage);
        }
        final HashMap<String, TableInfo.Column> _columnsFlowStats = new HashMap<String, TableInfo.Column>(8);
        _columnsFlowStats.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlowStats.put("appUid", new TableInfo.Column("appUid", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlowStats.put("packageName", new TableInfo.Column("packageName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlowStats.put("firstSeen", new TableInfo.Column("firstSeen", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlowStats.put("lastSeen", new TableInfo.Column("lastSeen", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlowStats.put("packetCount", new TableInfo.Column("packetCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlowStats.put("totalBytes", new TableInfo.Column("totalBytes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlowStats.put("trafficType", new TableInfo.Column("trafficType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFlowStats = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFlowStats = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFlowStats = new TableInfo("flow_stats", _columnsFlowStats, _foreignKeysFlowStats, _indicesFlowStats);
        final TableInfo _existingFlowStats = TableInfo.read(db, "flow_stats");
        if (!_infoFlowStats.equals(_existingFlowStats)) {
          return new RoomOpenHelper.ValidationResult(false, "flow_stats(com.datasentry.app.data.model.FlowStats).\n"
                  + " Expected:\n" + _infoFlowStats + "\n"
                  + " Found:\n" + _existingFlowStats);
        }
        final HashMap<String, TableInfo.Column> _columnsSuspiciousEvents = new HashMap<String, TableInfo.Column>(10);
        _columnsSuspiciousEvents.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuspiciousEvents.put("appUid", new TableInfo.Column("appUid", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuspiciousEvents.put("packageName", new TableInfo.Column("packageName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuspiciousEvents.put("trafficType", new TableInfo.Column("trafficType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuspiciousEvents.put("riskLevel", new TableInfo.Column("riskLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuspiciousEvents.put("totalBytes", new TableInfo.Column("totalBytes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuspiciousEvents.put("reason", new TableInfo.Column("reason", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuspiciousEvents.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuspiciousEvents.put("protocol", new TableInfo.Column("protocol", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuspiciousEvents.put("destinationPort", new TableInfo.Column("destinationPort", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSuspiciousEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSuspiciousEvents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSuspiciousEvents = new TableInfo("suspicious_events", _columnsSuspiciousEvents, _foreignKeysSuspiciousEvents, _indicesSuspiciousEvents);
        final TableInfo _existingSuspiciousEvents = TableInfo.read(db, "suspicious_events");
        if (!_infoSuspiciousEvents.equals(_existingSuspiciousEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "suspicious_events(com.datasentry.app.data.model.SuspiciousEvent).\n"
                  + " Expected:\n" + _infoSuspiciousEvents + "\n"
                  + " Found:\n" + _existingSuspiciousEvents);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "a212f338be8d727a9fafd6d4e59f0989", "66cd1974baec3319bc4dcea2d25bd67a");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "packets","app_usage","flow_stats","suspicious_events");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `packets`");
      _db.execSQL("DELETE FROM `app_usage`");
      _db.execSQL("DELETE FROM `flow_stats`");
      _db.execSQL("DELETE FROM `suspicious_events`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PacketDao.class, PacketDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AppUsageDao.class, AppUsageDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FlowStatsDao.class, FlowStatsDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SuspiciousEventDao.class, SuspiciousEventDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PacketDao packetDao() {
    if (_packetDao != null) {
      return _packetDao;
    } else {
      synchronized(this) {
        if(_packetDao == null) {
          _packetDao = new PacketDao_Impl(this);
        }
        return _packetDao;
      }
    }
  }

  @Override
  public AppUsageDao appUsageDao() {
    if (_appUsageDao != null) {
      return _appUsageDao;
    } else {
      synchronized(this) {
        if(_appUsageDao == null) {
          _appUsageDao = new AppUsageDao_Impl(this);
        }
        return _appUsageDao;
      }
    }
  }

  @Override
  public FlowStatsDao flowStatsDao() {
    if (_flowStatsDao != null) {
      return _flowStatsDao;
    } else {
      synchronized(this) {
        if(_flowStatsDao == null) {
          _flowStatsDao = new FlowStatsDao_Impl(this);
        }
        return _flowStatsDao;
      }
    }
  }

  @Override
  public SuspiciousEventDao suspiciousEventDao() {
    if (_suspiciousEventDao != null) {
      return _suspiciousEventDao;
    } else {
      synchronized(this) {
        if(_suspiciousEventDao == null) {
          _suspiciousEventDao = new SuspiciousEventDao_Impl(this);
        }
        return _suspiciousEventDao;
      }
    }
  }
}
