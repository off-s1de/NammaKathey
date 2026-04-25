package com.nammakathey.app.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.nammakathey.app.data.model.ProgressEntity;
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

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProgressDao_Impl implements ProgressDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProgressEntity> __insertionAdapterOfProgressEntity;

  public ProgressDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProgressEntity = new EntityInsertionAdapter<ProgressEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `progress` (`heroId`,`districtId`,`storiesRead`,`quizCompleted`,`lastReadAt`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProgressEntity entity) {
        statement.bindString(1, entity.getHeroId());
        statement.bindString(2, entity.getDistrictId());
        statement.bindLong(3, entity.getStoriesRead());
        final int _tmp = entity.getQuizCompleted() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindLong(5, entity.getLastReadAt());
      }
    };
  }

  @Override
  public Object upsertProgress(final ProgressEntity progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProgressEntity.insert(progress);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getProgress(final String heroId,
      final Continuation<? super ProgressEntity> $completion) {
    final String _sql = "SELECT * FROM progress WHERE heroId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, heroId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ProgressEntity>() {
      @Override
      @Nullable
      public ProgressEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfHeroId = CursorUtil.getColumnIndexOrThrow(_cursor, "heroId");
          final int _cursorIndexOfDistrictId = CursorUtil.getColumnIndexOrThrow(_cursor, "districtId");
          final int _cursorIndexOfStoriesRead = CursorUtil.getColumnIndexOrThrow(_cursor, "storiesRead");
          final int _cursorIndexOfQuizCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "quizCompleted");
          final int _cursorIndexOfLastReadAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReadAt");
          final ProgressEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpHeroId;
            _tmpHeroId = _cursor.getString(_cursorIndexOfHeroId);
            final String _tmpDistrictId;
            _tmpDistrictId = _cursor.getString(_cursorIndexOfDistrictId);
            final int _tmpStoriesRead;
            _tmpStoriesRead = _cursor.getInt(_cursorIndexOfStoriesRead);
            final boolean _tmpQuizCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfQuizCompleted);
            _tmpQuizCompleted = _tmp != 0;
            final long _tmpLastReadAt;
            _tmpLastReadAt = _cursor.getLong(_cursorIndexOfLastReadAt);
            _result = new ProgressEntity(_tmpHeroId,_tmpDistrictId,_tmpStoriesRead,_tmpQuizCompleted,_tmpLastReadAt);
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
  public Object getDistrictProgress(final String districtId,
      final Continuation<? super List<ProgressEntity>> $completion) {
    final String _sql = "SELECT * FROM progress WHERE districtId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, districtId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ProgressEntity>>() {
      @Override
      @NonNull
      public List<ProgressEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfHeroId = CursorUtil.getColumnIndexOrThrow(_cursor, "heroId");
          final int _cursorIndexOfDistrictId = CursorUtil.getColumnIndexOrThrow(_cursor, "districtId");
          final int _cursorIndexOfStoriesRead = CursorUtil.getColumnIndexOrThrow(_cursor, "storiesRead");
          final int _cursorIndexOfQuizCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "quizCompleted");
          final int _cursorIndexOfLastReadAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReadAt");
          final List<ProgressEntity> _result = new ArrayList<ProgressEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProgressEntity _item;
            final String _tmpHeroId;
            _tmpHeroId = _cursor.getString(_cursorIndexOfHeroId);
            final String _tmpDistrictId;
            _tmpDistrictId = _cursor.getString(_cursorIndexOfDistrictId);
            final int _tmpStoriesRead;
            _tmpStoriesRead = _cursor.getInt(_cursorIndexOfStoriesRead);
            final boolean _tmpQuizCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfQuizCompleted);
            _tmpQuizCompleted = _tmp != 0;
            final long _tmpLastReadAt;
            _tmpLastReadAt = _cursor.getLong(_cursorIndexOfLastReadAt);
            _item = new ProgressEntity(_tmpHeroId,_tmpDistrictId,_tmpStoriesRead,_tmpQuizCompleted,_tmpLastReadAt);
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
  public Object getCompletedCountForDistrict(final String districtId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM progress WHERE districtId = ? AND quizCompleted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, districtId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
