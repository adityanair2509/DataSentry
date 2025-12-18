package com.datasentry.app.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.datasentry.app.data.local.entity.PacketEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class PacketDao_Impl implements PacketDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PacketEntity> __insertionAdapterOfPacketEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public PacketDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPacketEntity = new EntityInsertionAdapter<PacketEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `packets` (`id`,`timestamp`,`sourceIp`,`destIp`,`protocol`,`sizeBytes`,`appName`,`contentType`,`isRisk`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PacketEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTimestamp());
        if (entity.getSourceIp() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSourceIp());
        }
        if (entity.getDestIp() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDestIp());
        }
        if (entity.getProtocol() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getProtocol());
        }
        statement.bindLong(6, entity.getSizeBytes());
        if (entity.getAppName() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getAppName());
        }
        if (entity.getContentType() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getContentType());
        }
        final int _tmp = entity.isRisk() ? 1 : 0;
        statement.bindLong(9, _tmp);
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM packets";
        return _query;
      }
    };
  }

  @Override
  public Object insertPacket(final PacketEntity packet,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPacketEntity.insert(packet);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
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
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PacketEntity>> getAllPackets() {
    final String _sql = "SELECT * FROM packets ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"packets"}, new Callable<List<PacketEntity>>() {
      @Override
      @NonNull
      public List<PacketEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSourceIp = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceIp");
          final int _cursorIndexOfDestIp = CursorUtil.getColumnIndexOrThrow(_cursor, "destIp");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeBytes");
          final int _cursorIndexOfAppName = CursorUtil.getColumnIndexOrThrow(_cursor, "appName");
          final int _cursorIndexOfContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "contentType");
          final int _cursorIndexOfIsRisk = CursorUtil.getColumnIndexOrThrow(_cursor, "isRisk");
          final List<PacketEntity> _result = new ArrayList<PacketEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PacketEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpSourceIp;
            if (_cursor.isNull(_cursorIndexOfSourceIp)) {
              _tmpSourceIp = null;
            } else {
              _tmpSourceIp = _cursor.getString(_cursorIndexOfSourceIp);
            }
            final String _tmpDestIp;
            if (_cursor.isNull(_cursorIndexOfDestIp)) {
              _tmpDestIp = null;
            } else {
              _tmpDestIp = _cursor.getString(_cursorIndexOfDestIp);
            }
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final int _tmpSizeBytes;
            _tmpSizeBytes = _cursor.getInt(_cursorIndexOfSizeBytes);
            final String _tmpAppName;
            if (_cursor.isNull(_cursorIndexOfAppName)) {
              _tmpAppName = null;
            } else {
              _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            }
            final String _tmpContentType;
            if (_cursor.isNull(_cursorIndexOfContentType)) {
              _tmpContentType = null;
            } else {
              _tmpContentType = _cursor.getString(_cursorIndexOfContentType);
            }
            final boolean _tmpIsRisk;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsRisk);
            _tmpIsRisk = _tmp != 0;
            _item = new PacketEntity(_tmpId,_tmpTimestamp,_tmpSourceIp,_tmpDestIp,_tmpProtocol,_tmpSizeBytes,_tmpAppName,_tmpContentType,_tmpIsRisk);
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
  public Flow<Integer> getRiskCount() {
    final String _sql = "SELECT COUNT(*) FROM packets WHERE isRisk = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"packets"}, new Callable<Integer>() {
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
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
