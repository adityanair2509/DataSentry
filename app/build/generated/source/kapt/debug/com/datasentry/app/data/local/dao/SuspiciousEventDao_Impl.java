package com.datasentry.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.datasentry.app.data.local.converter.RiskLevelConverter;
import com.datasentry.app.data.local.converter.TrafficTypeConverter;
import com.datasentry.app.data.model.RiskLevel;
import com.datasentry.app.data.model.SuspiciousEvent;
import com.datasentry.app.data.model.TrafficType;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class SuspiciousEventDao_Impl implements SuspiciousEventDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SuspiciousEvent> __insertionAdapterOfSuspiciousEvent;

  private final TrafficTypeConverter __trafficTypeConverter = new TrafficTypeConverter();

  private final RiskLevelConverter __riskLevelConverter = new RiskLevelConverter();

  private final EntityDeletionOrUpdateAdapter<SuspiciousEvent> __updateAdapterOfSuspiciousEvent;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByPackage;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOlderThan;

  public SuspiciousEventDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSuspiciousEvent = new EntityInsertionAdapter<SuspiciousEvent>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `suspicious_events` (`id`,`appUid`,`packageName`,`trafficType`,`riskLevel`,`totalBytes`,`reason`,`timestamp`,`protocol`,`destinationPort`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SuspiciousEvent entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAppUid());
        if (entity.getPackageName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPackageName());
        }
        final String _tmp = __trafficTypeConverter.fromTrafficType(entity.getTrafficType());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        final String _tmp_1 = __riskLevelConverter.fromRiskLevel(entity.getRiskLevel());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_1);
        }
        statement.bindLong(6, entity.getTotalBytes());
        if (entity.getReason() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getReason());
        }
        statement.bindLong(8, entity.getTimestamp());
        if (entity.getProtocol() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getProtocol());
        }
        if (entity.getDestinationPort() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getDestinationPort());
        }
      }
    };
    this.__updateAdapterOfSuspiciousEvent = new EntityDeletionOrUpdateAdapter<SuspiciousEvent>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `suspicious_events` SET `id` = ?,`appUid` = ?,`packageName` = ?,`trafficType` = ?,`riskLevel` = ?,`totalBytes` = ?,`reason` = ?,`timestamp` = ?,`protocol` = ?,`destinationPort` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SuspiciousEvent entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAppUid());
        if (entity.getPackageName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPackageName());
        }
        final String _tmp = __trafficTypeConverter.fromTrafficType(entity.getTrafficType());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        final String _tmp_1 = __riskLevelConverter.fromRiskLevel(entity.getRiskLevel());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_1);
        }
        statement.bindLong(6, entity.getTotalBytes());
        if (entity.getReason() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getReason());
        }
        statement.bindLong(8, entity.getTimestamp());
        if (entity.getProtocol() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getProtocol());
        }
        if (entity.getDestinationPort() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getDestinationPort());
        }
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM suspicious_events";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByPackage = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM suspicious_events WHERE packageName = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOlderThan = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM suspicious_events WHERE timestamp < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final SuspiciousEvent event, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSuspiciousEvent.insertAndReturnId(event);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final SuspiciousEvent event, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSuspiciousEvent.handle(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
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
  public Flow<List<SuspiciousEvent>> observeAll() {
    final String _sql = "SELECT * FROM suspicious_events ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"suspicious_events"}, new Callable<List<SuspiciousEvent>>() {
      @Override
      @NonNull
      public List<SuspiciousEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final int _cursorIndexOfRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "riskLevel");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfDestinationPort = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationPort");
          final List<SuspiciousEvent> _result = new ArrayList<SuspiciousEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SuspiciousEvent _item;
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
            final TrafficType _tmpTrafficType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp);
            final RiskLevel _tmpRiskLevel;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRiskLevel)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRiskLevel);
            }
            _tmpRiskLevel = __riskLevelConverter.toRiskLevel(_tmp_1);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final String _tmpReason;
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _tmpReason = null;
            } else {
              _tmpReason = _cursor.getString(_cursorIndexOfReason);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final Integer _tmpDestinationPort;
            if (_cursor.isNull(_cursorIndexOfDestinationPort)) {
              _tmpDestinationPort = null;
            } else {
              _tmpDestinationPort = _cursor.getInt(_cursorIndexOfDestinationPort);
            }
            _item = new SuspiciousEvent(_tmpId,_tmpAppUid,_tmpPackageName,_tmpTrafficType,_tmpRiskLevel,_tmpTotalBytes,_tmpReason,_tmpTimestamp,_tmpProtocol,_tmpDestinationPort);
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
  public Flow<List<SuspiciousEvent>> observeByPackage(final String packageName) {
    final String _sql = "SELECT * FROM suspicious_events WHERE packageName = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (packageName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, packageName);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"suspicious_events"}, new Callable<List<SuspiciousEvent>>() {
      @Override
      @NonNull
      public List<SuspiciousEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final int _cursorIndexOfRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "riskLevel");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfDestinationPort = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationPort");
          final List<SuspiciousEvent> _result = new ArrayList<SuspiciousEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SuspiciousEvent _item;
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
            final TrafficType _tmpTrafficType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp);
            final RiskLevel _tmpRiskLevel;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRiskLevel)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRiskLevel);
            }
            _tmpRiskLevel = __riskLevelConverter.toRiskLevel(_tmp_1);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final String _tmpReason;
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _tmpReason = null;
            } else {
              _tmpReason = _cursor.getString(_cursorIndexOfReason);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final Integer _tmpDestinationPort;
            if (_cursor.isNull(_cursorIndexOfDestinationPort)) {
              _tmpDestinationPort = null;
            } else {
              _tmpDestinationPort = _cursor.getInt(_cursorIndexOfDestinationPort);
            }
            _item = new SuspiciousEvent(_tmpId,_tmpAppUid,_tmpPackageName,_tmpTrafficType,_tmpRiskLevel,_tmpTotalBytes,_tmpReason,_tmpTimestamp,_tmpProtocol,_tmpDestinationPort);
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
  public Flow<List<SuspiciousEvent>> observeByRiskLevel(final RiskLevel riskLevel) {
    final String _sql = "SELECT * FROM suspicious_events WHERE riskLevel = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __riskLevelConverter.fromRiskLevel(riskLevel);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"suspicious_events"}, new Callable<List<SuspiciousEvent>>() {
      @Override
      @NonNull
      public List<SuspiciousEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final int _cursorIndexOfRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "riskLevel");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfDestinationPort = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationPort");
          final List<SuspiciousEvent> _result = new ArrayList<SuspiciousEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SuspiciousEvent _item;
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
            final TrafficType _tmpTrafficType;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp_1);
            final RiskLevel _tmpRiskLevel;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfRiskLevel)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfRiskLevel);
            }
            _tmpRiskLevel = __riskLevelConverter.toRiskLevel(_tmp_2);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final String _tmpReason;
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _tmpReason = null;
            } else {
              _tmpReason = _cursor.getString(_cursorIndexOfReason);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final Integer _tmpDestinationPort;
            if (_cursor.isNull(_cursorIndexOfDestinationPort)) {
              _tmpDestinationPort = null;
            } else {
              _tmpDestinationPort = _cursor.getInt(_cursorIndexOfDestinationPort);
            }
            _item = new SuspiciousEvent(_tmpId,_tmpAppUid,_tmpPackageName,_tmpTrafficType,_tmpRiskLevel,_tmpTotalBytes,_tmpReason,_tmpTimestamp,_tmpProtocol,_tmpDestinationPort);
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
  public Object getAll(final Continuation<? super List<SuspiciousEvent>> $completion) {
    final String _sql = "SELECT * FROM suspicious_events ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SuspiciousEvent>>() {
      @Override
      @NonNull
      public List<SuspiciousEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final int _cursorIndexOfRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "riskLevel");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfDestinationPort = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationPort");
          final List<SuspiciousEvent> _result = new ArrayList<SuspiciousEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SuspiciousEvent _item;
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
            final TrafficType _tmpTrafficType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp);
            final RiskLevel _tmpRiskLevel;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRiskLevel)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRiskLevel);
            }
            _tmpRiskLevel = __riskLevelConverter.toRiskLevel(_tmp_1);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final String _tmpReason;
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _tmpReason = null;
            } else {
              _tmpReason = _cursor.getString(_cursorIndexOfReason);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final Integer _tmpDestinationPort;
            if (_cursor.isNull(_cursorIndexOfDestinationPort)) {
              _tmpDestinationPort = null;
            } else {
              _tmpDestinationPort = _cursor.getInt(_cursorIndexOfDestinationPort);
            }
            _item = new SuspiciousEvent(_tmpId,_tmpAppUid,_tmpPackageName,_tmpTrafficType,_tmpRiskLevel,_tmpTotalBytes,_tmpReason,_tmpTimestamp,_tmpProtocol,_tmpDestinationPort);
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
      final Continuation<? super List<SuspiciousEvent>> $completion) {
    final String _sql = "SELECT * FROM suspicious_events WHERE packageName = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (packageName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, packageName);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SuspiciousEvent>>() {
      @Override
      @NonNull
      public List<SuspiciousEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final int _cursorIndexOfRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "riskLevel");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfDestinationPort = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationPort");
          final List<SuspiciousEvent> _result = new ArrayList<SuspiciousEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SuspiciousEvent _item;
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
            final TrafficType _tmpTrafficType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp);
            final RiskLevel _tmpRiskLevel;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRiskLevel)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRiskLevel);
            }
            _tmpRiskLevel = __riskLevelConverter.toRiskLevel(_tmp_1);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final String _tmpReason;
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _tmpReason = null;
            } else {
              _tmpReason = _cursor.getString(_cursorIndexOfReason);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final Integer _tmpDestinationPort;
            if (_cursor.isNull(_cursorIndexOfDestinationPort)) {
              _tmpDestinationPort = null;
            } else {
              _tmpDestinationPort = _cursor.getInt(_cursorIndexOfDestinationPort);
            }
            _item = new SuspiciousEvent(_tmpId,_tmpAppUid,_tmpPackageName,_tmpTrafficType,_tmpRiskLevel,_tmpTotalBytes,_tmpReason,_tmpTimestamp,_tmpProtocol,_tmpDestinationPort);
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
  public Object getByRiskLevel(final RiskLevel riskLevel,
      final Continuation<? super List<SuspiciousEvent>> $completion) {
    final String _sql = "SELECT * FROM suspicious_events WHERE riskLevel = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __riskLevelConverter.fromRiskLevel(riskLevel);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SuspiciousEvent>>() {
      @Override
      @NonNull
      public List<SuspiciousEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final int _cursorIndexOfRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "riskLevel");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfDestinationPort = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationPort");
          final List<SuspiciousEvent> _result = new ArrayList<SuspiciousEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SuspiciousEvent _item;
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
            final TrafficType _tmpTrafficType;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp_1);
            final RiskLevel _tmpRiskLevel;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfRiskLevel)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfRiskLevel);
            }
            _tmpRiskLevel = __riskLevelConverter.toRiskLevel(_tmp_2);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final String _tmpReason;
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _tmpReason = null;
            } else {
              _tmpReason = _cursor.getString(_cursorIndexOfReason);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final Integer _tmpDestinationPort;
            if (_cursor.isNull(_cursorIndexOfDestinationPort)) {
              _tmpDestinationPort = null;
            } else {
              _tmpDestinationPort = _cursor.getInt(_cursorIndexOfDestinationPort);
            }
            _item = new SuspiciousEvent(_tmpId,_tmpAppUid,_tmpPackageName,_tmpTrafficType,_tmpRiskLevel,_tmpTotalBytes,_tmpReason,_tmpTimestamp,_tmpProtocol,_tmpDestinationPort);
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
      final Continuation<? super List<SuspiciousEvent>> $completion) {
    final String _sql = "SELECT * FROM suspicious_events WHERE trafficType = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __trafficTypeConverter.fromTrafficType(trafficType);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SuspiciousEvent>>() {
      @Override
      @NonNull
      public List<SuspiciousEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final int _cursorIndexOfRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "riskLevel");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfDestinationPort = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationPort");
          final List<SuspiciousEvent> _result = new ArrayList<SuspiciousEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SuspiciousEvent _item;
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
            final TrafficType _tmpTrafficType;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp_1);
            final RiskLevel _tmpRiskLevel;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfRiskLevel)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfRiskLevel);
            }
            _tmpRiskLevel = __riskLevelConverter.toRiskLevel(_tmp_2);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final String _tmpReason;
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _tmpReason = null;
            } else {
              _tmpReason = _cursor.getString(_cursorIndexOfReason);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final Integer _tmpDestinationPort;
            if (_cursor.isNull(_cursorIndexOfDestinationPort)) {
              _tmpDestinationPort = null;
            } else {
              _tmpDestinationPort = _cursor.getInt(_cursorIndexOfDestinationPort);
            }
            _item = new SuspiciousEvent(_tmpId,_tmpAppUid,_tmpPackageName,_tmpTrafficType,_tmpRiskLevel,_tmpTotalBytes,_tmpReason,_tmpTimestamp,_tmpProtocol,_tmpDestinationPort);
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
      final Continuation<? super List<SuspiciousEvent>> $completion) {
    final String _sql = "SELECT * FROM suspicious_events WHERE timestamp >= ? AND timestamp <= ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startTime);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endTime);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SuspiciousEvent>>() {
      @Override
      @NonNull
      public List<SuspiciousEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final int _cursorIndexOfRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "riskLevel");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfDestinationPort = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationPort");
          final List<SuspiciousEvent> _result = new ArrayList<SuspiciousEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SuspiciousEvent _item;
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
            final TrafficType _tmpTrafficType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp);
            final RiskLevel _tmpRiskLevel;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRiskLevel)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRiskLevel);
            }
            _tmpRiskLevel = __riskLevelConverter.toRiskLevel(_tmp_1);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final String _tmpReason;
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _tmpReason = null;
            } else {
              _tmpReason = _cursor.getString(_cursorIndexOfReason);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final Integer _tmpDestinationPort;
            if (_cursor.isNull(_cursorIndexOfDestinationPort)) {
              _tmpDestinationPort = null;
            } else {
              _tmpDestinationPort = _cursor.getInt(_cursorIndexOfDestinationPort);
            }
            _item = new SuspiciousEvent(_tmpId,_tmpAppUid,_tmpPackageName,_tmpTrafficType,_tmpRiskLevel,_tmpTotalBytes,_tmpReason,_tmpTimestamp,_tmpProtocol,_tmpDestinationPort);
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
  public Object getCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM suspicious_events";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
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
  public Object getCountByRiskLevel(final RiskLevel riskLevel,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM suspicious_events WHERE riskLevel = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __riskLevelConverter.fromRiskLevel(riskLevel);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp_1;
            if (_cursor.isNull(0)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(0);
            }
            _result = _tmp_1;
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
  public Object getLatest(final Continuation<? super SuspiciousEvent> $completion) {
    final String _sql = "SELECT * FROM suspicious_events ORDER BY timestamp DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SuspiciousEvent>() {
      @Override
      @Nullable
      public SuspiciousEvent call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final int _cursorIndexOfRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "riskLevel");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfDestinationPort = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationPort");
          final SuspiciousEvent _result;
          if (_cursor.moveToFirst()) {
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
            final TrafficType _tmpTrafficType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp);
            final RiskLevel _tmpRiskLevel;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRiskLevel)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRiskLevel);
            }
            _tmpRiskLevel = __riskLevelConverter.toRiskLevel(_tmp_1);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final String _tmpReason;
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _tmpReason = null;
            } else {
              _tmpReason = _cursor.getString(_cursorIndexOfReason);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final Integer _tmpDestinationPort;
            if (_cursor.isNull(_cursorIndexOfDestinationPort)) {
              _tmpDestinationPort = null;
            } else {
              _tmpDestinationPort = _cursor.getInt(_cursorIndexOfDestinationPort);
            }
            _result = new SuspiciousEvent(_tmpId,_tmpAppUid,_tmpPackageName,_tmpTrafficType,_tmpRiskLevel,_tmpTotalBytes,_tmpReason,_tmpTimestamp,_tmpProtocol,_tmpDestinationPort);
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
  public Object getRecent(final int limit,
      final Continuation<? super List<SuspiciousEvent>> $completion) {
    final String _sql = "SELECT * FROM suspicious_events ORDER BY timestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SuspiciousEvent>>() {
      @Override
      @NonNull
      public List<SuspiciousEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppUid = CursorUtil.getColumnIndexOrThrow(_cursor, "appUid");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfTrafficType = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficType");
          final int _cursorIndexOfRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "riskLevel");
          final int _cursorIndexOfTotalBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBytes");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfDestinationPort = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationPort");
          final List<SuspiciousEvent> _result = new ArrayList<SuspiciousEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SuspiciousEvent _item;
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
            final TrafficType _tmpTrafficType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTrafficType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTrafficType);
            }
            _tmpTrafficType = __trafficTypeConverter.toTrafficType(_tmp);
            final RiskLevel _tmpRiskLevel;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRiskLevel)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRiskLevel);
            }
            _tmpRiskLevel = __riskLevelConverter.toRiskLevel(_tmp_1);
            final long _tmpTotalBytes;
            _tmpTotalBytes = _cursor.getLong(_cursorIndexOfTotalBytes);
            final String _tmpReason;
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _tmpReason = null;
            } else {
              _tmpReason = _cursor.getString(_cursorIndexOfReason);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final Integer _tmpDestinationPort;
            if (_cursor.isNull(_cursorIndexOfDestinationPort)) {
              _tmpDestinationPort = null;
            } else {
              _tmpDestinationPort = _cursor.getInt(_cursorIndexOfDestinationPort);
            }
            _item = new SuspiciousEvent(_tmpId,_tmpAppUid,_tmpPackageName,_tmpTrafficType,_tmpRiskLevel,_tmpTotalBytes,_tmpReason,_tmpTimestamp,_tmpProtocol,_tmpDestinationPort);
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
