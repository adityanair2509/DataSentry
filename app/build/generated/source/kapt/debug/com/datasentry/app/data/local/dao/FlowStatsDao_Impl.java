package com.datasentry.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.EntityUpsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.datasentry.app.data.local.converter.TrafficTypeConverter;
import com.datasentry.app.data.model.FlowStats;
import com.datasentry.app.data.model.TrafficType;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FlowStatsDao_Impl implements FlowStatsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FlowStats> __insertionAdapterOfFlowStats;

  private final TrafficTypeConverter __trafficTypeConverter = new TrafficTypeConverter();

  private final EntityDeletionOrUpdateAdapter<FlowStats> __updateAdapterOfFlowStats;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTrafficType;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByPackage;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOlderThan;

  private final EntityUpsertionAdapter<FlowStats> __upsertionAdapterOfFlowStats;

  public FlowStatsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFlowStats = new EntityInsertionAdapter<FlowStats>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `flow_stats` (`id`,`appUid`,`packageName`,`firstSeen`,`lastSeen`,`packetCount`,`totalBytes`,`trafficType`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FlowStats entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAppUid());
        if (entity.getPackageName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPackageName());
        }
        statement.bindLong(4, entity.getFirstSeen());
        statement.bindLong(5, entity.getLastSeen());
        statement.bindLong(6, entity.getPacketCount());
        statement.bindLong(7, entity.getTotalBytes());
        final String _tmp = __trafficTypeConverter.fromTrafficType(entity.getTrafficType());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp);
        }
      }
    };
    this.__updateAdapterOfFlowStats = new EntityDeletionOrUpdateAdapter<FlowStats>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `flow_stats` SET `id` = ?,`appUid` = ?,`packageName` = ?,`firstSeen` = ?,`lastSeen` = ?,`packetCount` = ?,`totalBytes` = ?,`trafficType` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FlowStats entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAppUid());
        if (entity.getPackageName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPackageName());
        }
        statement.bindLong(4, entity.getFirstSeen());
        statement.bindLong(5, entity.getLastSeen());
        statement.bindLong(6, entity.getPacketCount());
        statement.bindLong(7, entity.getTotalBytes());
        final String _tmp = __trafficTypeConverter.fromTrafficType(entity.getTrafficType());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp);
        }
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateTrafficType = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE flow_stats SET trafficType = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM flow_stats";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByPackage = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM flow_stats WHERE packageName = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOlderThan = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM flow_stats WHERE firstSeen < ?";
        return _query;
      }
    };
    this.__upsertionAdapterOfFlowStats = new EntityUpsertionAdapter<FlowStats>(new EntityInsertionAdapter<FlowStats>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT INTO `flow_stats` (`id`,`appUid`,`packageName`,`firstSeen`,`lastSeen`,`packetCount`,`totalBytes`,`trafficType`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FlowStats entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAppUid());
        if (entity.getPackageName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPackageName());
        }
        statement.bindLong(4, entity.getFirstSeen());
        statement.bindLong(5, entity.getLastSeen());
        statement.bindLong(6, entity.getPacketCount());
        statement.bindLong(7, entity.getTotalBytes());
        final String _tmp = __trafficTypeConverter.fromTrafficType(entity.getTrafficType());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp);
        }
      }
    }, new EntityDeletionOrUpdateAdapter<FlowStats>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE `flow_stats` SET `id` = ?,`appUid` = ?,`packageName` = ?,`firstSeen` = ?,`lastSeen` = ?,`packetCount` = ?,`totalBytes` = ?,`trafficType` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FlowStats entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAppUid());
        if (entity.getPackageName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPackageName());
        }
        statement.bindLong(4, entity.getFirstSeen());
        statement.bindLong(5, entity.getLastSeen());
        statement.bindLong(6, entity.getPacketCount());
        statement.bindLong(7, entity.getTotalBytes());
        final String _tmp = __trafficTypeConverter.fromTrafficType(entity.getTrafficType());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp);
        }
        statement.bindLong(9, entity.getId());
      }
    });
  }

  @Override
  public Object insert(final FlowStats flowStats, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfFlowStats.insertAndReturnId(flowStats);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final FlowStats flowStats, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfFlowStats.handle(flowStats);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTrafficType(final long id, final TrafficType trafficType,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTrafficType.acquire();
        int _argIndex = 1;
        final String _tmp = __trafficTypeConverter.fromTrafficType(trafficType);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, _tmp);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateTrafficType.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByPackage(final String packageName,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByPackage.acquire();
        int _argIndex = 1;
        if (packageName == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, packageName);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteByPackage.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOlderThan(final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOlderThan.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOlderThan.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object upsert(final FlowStats flowStats, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __upsertionAdapterOfFlowStats.upsert(flowStats);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<FlowStats>> observeAll() {
    final String _sql = "SELECT * FROM flow_stats ORDER BY lastSeen DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"flow_stats"}, new Callable<List<FlowStats>>() {
      @Override
      @NonNull
      public List<FlowStats> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfFirstSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSeen");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfPacketCount = CursorUtil.getColumnIndexOrThrow(_cursor, "packetCount");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final List<FlowStats> _result = new ArrayList<FlowStats>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlowStats _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpAppUid;
            _tmpAppUid = _cursor.getInt(_cursorIndexOfAppUid);
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final long _tmpFirstSeen;
            _tmpFirstSeen = _cursor.getLong(_cursorIndexOfFirstSeen);
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final int _tmpPacketCount;
            _tmpPacketCount = _cursor.getInt(_cursorIndexOfPacketCount);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final TrafficType _tmpTrafficType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp);
            _item = new FlowStats(_tmpId,_tmpAppUid,_tmpPackageName,_tmpFirstSeen,_tmpLastSeen,_tmpPacketCount,_tmpTotalBytes,_tmpTrafficType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<FlowStats>> observeByPackage(final String packageName) {
    final String _sql = "SELECT * FROM flow_stats WHERE packageName = ? ORDER BY lastSeen DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (packageName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, packageName);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"flow_stats"}, new Callable<List<FlowStats>>() {
      @Override
      @NonNull
      public List<FlowStats> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfFirstSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSeen");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfPacketCount = CursorUtil.getColumnIndexOrThrow(_cursor, "packetCount");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final List<FlowStats> _result = new ArrayList<FlowStats>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlowStats _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpAppUid;
            _tmpAppUid = _cursor.getInt(_cursorIndexOfAppUid);
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final long _tmpFirstSeen;
            _tmpFirstSeen = _cursor.getLong(_cursorIndexOfFirstSeen);
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final int _tmpPacketCount;
            _tmpPacketCount = _cursor.getInt(_cursorIndexOfPacketCount);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final TrafficType _tmpTrafficType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp);
            _item = new FlowStats(_tmpId,_tmpAppUid,_tmpPackageName,_tmpFirstSeen,_tmpLastSeen,_tmpPacketCount,_tmpTotalBytes,_tmpTrafficType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAll(final Continuation<? super List<FlowStats>> $completion) {
    final String _sql = "SELECT * FROM flow_stats ORDER BY lastSeen DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FlowStats>>() {
      @Override
      @NonNull
      public List<FlowStats> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfFirstSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSeen");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfPacketCount = CursorUtil.getColumnIndexOrThrow(_cursor, "packetCount");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final List<FlowStats> _result = new ArrayList<FlowStats>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlowStats _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpAppUid;
            _tmpAppUid = _cursor.getInt(_cursorIndexOfAppUid);
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final long _tmpFirstSeen;
            _tmpFirstSeen = _cursor.getLong(_cursorIndexOfFirstSeen);
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final int _tmpPacketCount;
            _tmpPacketCount = _cursor.getInt(_cursorIndexOfPacketCount);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final TrafficType _tmpTrafficType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp);
            _item = new FlowStats(_tmpId,_tmpAppUid,_tmpPackageName,_tmpFirstSeen,_tmpLastSeen,_tmpPacketCount,_tmpTotalBytes,_tmpTrafficType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getByPackage(final String packageName,
      final Continuation<? super List<FlowStats>> $completion) {
    final String _sql = "SELECT * FROM flow_stats WHERE packageName = ? ORDER BY lastSeen DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (packageName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, packageName);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FlowStats>>() {
      @Override
      @NonNull
      public List<FlowStats> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfFirstSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSeen");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfPacketCount = CursorUtil.getColumnIndexOrThrow(_cursor, "packetCount");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final List<FlowStats> _result = new ArrayList<FlowStats>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlowStats _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpAppUid;
            _tmpAppUid = _cursor.getInt(_cursorIndexOfAppUid);
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final long _tmpFirstSeen;
            _tmpFirstSeen = _cursor.getLong(_cursorIndexOfFirstSeen);
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final int _tmpPacketCount;
            _tmpPacketCount = _cursor.getInt(_cursorIndexOfPacketCount);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final TrafficType _tmpTrafficType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp);
            _item = new FlowStats(_tmpId,_tmpAppUid,_tmpPackageName,_tmpFirstSeen,_tmpLastSeen,_tmpPacketCount,_tmpTotalBytes,_tmpTrafficType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getByTrafficType(final TrafficType trafficType,
      final Continuation<? super List<FlowStats>> $completion) {
    final String _sql = "SELECT * FROM flow_stats WHERE trafficType = ? ORDER BY lastSeen DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __trafficTypeConverter.fromTrafficType(trafficType);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FlowStats>>() {
      @Override
      @NonNull
      public List<FlowStats> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfFirstSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSeen");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfPacketCount = CursorUtil.getColumnIndexOrThrow(_cursor, "packetCount");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final List<FlowStats> _result = new ArrayList<FlowStats>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlowStats _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpAppUid;
            _tmpAppUid = _cursor.getInt(_cursorIndexOfAppUid);
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final long _tmpFirstSeen;
            _tmpFirstSeen = _cursor.getLong(_cursorIndexOfFirstSeen);
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final int _tmpPacketCount;
            _tmpPacketCount = _cursor.getInt(_cursorIndexOfPacketCount);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final TrafficType _tmpTrafficType;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp_1);
            _item = new FlowStats(_tmpId,_tmpAppUid,_tmpPackageName,_tmpFirstSeen,_tmpLastSeen,_tmpPacketCount,_tmpTotalBytes,_tmpTrafficType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getByTimeRange(final long startTime, final long endTime,
      final Continuation<? super List<FlowStats>> $completion) {
    final String _sql = "SELECT * FROM flow_stats WHERE firstSeen >= ? AND firstSeen <= ? ORDER BY lastSeen DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startTime);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endTime);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FlowStats>>() {
      @Override
      @NonNull
      public List<FlowStats> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfFirstSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSeen");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfPacketCount = CursorUtil.getColumnIndexOrThrow(_cursor, "packetCount");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final List<FlowStats> _result = new ArrayList<FlowStats>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlowStats _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpAppUid;
            _tmpAppUid = _cursor.getInt(_cursorIndexOfAppUid);
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final long _tmpFirstSeen;
            _tmpFirstSeen = _cursor.getLong(_cursorIndexOfFirstSeen);
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final int _tmpPacketCount;
            _tmpPacketCount = _cursor.getInt(_cursorIndexOfPacketCount);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final TrafficType _tmpTrafficType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp);
            _item = new FlowStats(_tmpId,_tmpAppUid,_tmpPackageName,_tmpFirstSeen,_tmpLastSeen,_tmpPacketCount,_tmpTotalBytes,_tmpTrafficType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalDataUsage(final Continuation<? super Long> $completion) {
    final String _sql = "SELECT SUM(totalBytes) FROM flow_stats";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final Long _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getDataUsageByPackage(final String packageName,
      final Continuation<? super Long> $completion) {
    final String _sql = "SELECT SUM(totalBytes) FROM flow_stats WHERE packageName = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (packageName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, packageName);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final Long _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getDataUsageByTrafficType(
      final Continuation<? super List<TrafficTypeUsage>> $completion) {
    final String _sql = "SELECT trafficType, SUM(totalBytes) as totalBytes FROM flow_stats GROUP BY trafficType";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TrafficTypeUsage>>() {
      @Override
      @NonNull
      public List<TrafficTypeUsage> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTrafficType = 0;
          final int _cursorIndexOfTotalBytes = 1;
          final List<TrafficTypeUsage> _result = new ArrayList<TrafficTypeUsage>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TrafficTypeUsage _item;
            final TrafficType _tmpTrafficType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            _item = new TrafficTypeUsage(_tmpTrafficType,_tmpTotalBytes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getDataUsageByApp(final Continuation<? super List<AppDataUsage>> $completion) {
    final String _sql = "SELECT packageName, SUM(totalBytes) as totalBytes FROM flow_stats WHERE packageName IS NOT NULL GROUP BY packageName";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AppDataUsage>>() {
      @Override
      @NonNull
      public List<AppDataUsage> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPackageName = 0;
          final int _cursorIndexOfTotalBytes = 1;
          final List<AppDataUsage> _result = new ArrayList<AppDataUsage>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AppDataUsage _item;
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            _item = new AppDataUsage(_tmpPackageName,_tmpTotalBytes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getMostRecentFlows(final Continuation<? super List<FlowStats>> $completion) {
    final String _sql = "SELECT * FROM flow_stats ORDER BY lastSeen DESC LIMIT 10";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FlowStats>>() {
      @Override
      @NonNull
      public List<FlowStats> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfFirstSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "firstSeen");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfPacketCount = CursorUtil.getColumnIndexOrThrow(_cursor, "packetCount");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final List<FlowStats> _result = new ArrayList<FlowStats>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FlowStats _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpAppUid;
            _tmpAppUid = _cursor.getInt(_cursorIndexOfAppUid);
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final long _tmpFirstSeen;
            _tmpFirstSeen = _cursor.getLong(_cursorIndexOfFirstSeen);
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final int _tmpPacketCount;
            _tmpPacketCount = _cursor.getInt(_cursorIndexOfPacketCount);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final TrafficType _tmpTrafficType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp);
            _item = new FlowStats(_tmpId,_tmpAppUid,_tmpPackageName,_tmpFirstSeen,_tmpLastSeen,_tmpPacketCount,_tmpTotalBytes,_tmpTrafficType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
