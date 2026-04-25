package com.nammakathey.app.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.nammakathey.app.data.model.BadgeEntity;
import java.lang.Boolean;
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
public final class BadgeDao_Impl implements BadgeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BadgeEntity> __insertionAdapterOfBadgeEntity;

  public BadgeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBadgeEntity = new EntityInsertionAdapter<BadgeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `badges` (`badgeId`,`heroId`,`heroNameEn`,`heroNameKn`,`badgeNameEn`,`badgeNameKn`,`districtId`,`districtName`,`districtNameKn`,`districtColorHex`,`heroEmoji`,`earnedDate`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BadgeEntity entity) {
        statement.bindString(1, entity.getBadgeId());
        statement.bindString(2, entity.getHeroId());
        statement.bindString(3, entity.getHeroNameEn());
        statement.bindString(4, entity.getHeroNameKn());
        statement.bindString(5, entity.getBadgeNameEn());
        statement.bindString(6, entity.getBadgeNameKn());
        statement.bindString(7, entity.getDistrictId());
        statement.bindString(8, entity.getDistrictName());
        statement.bindString(9, entity.getDistrictNameKn());
        statement.bindString(10, entity.getDistrictColorHex());
        statement.bindString(11, entity.getHeroEmoji());
        statement.bindLong(12, entity.getEarnedDate());
      }
    };
  }

  @Override
  public Object insertBadge(final BadgeEntity badge, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBadgeEntity.insert(badge);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<BadgeEntity>> getAllBadges() {
    final String _sql = "SELECT * FROM badges ORDER BY earnedDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"badges"}, false, new Callable<List<BadgeEntity>>() {
      @Override
      @Nullable
      public List<BadgeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfBadgeId = CursorUtil.getColumnIndexOrThrow(_cursor, "badgeId");
          final int _cursorIndexOfHeroId = CursorUtil.getColumnIndexOrThrow(_cursor, "heroId");
          final int _cursorIndexOfHeroNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "heroNameEn");
          final int _cursorIndexOfHeroNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "heroNameKn");
          final int _cursorIndexOfBadgeNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "badgeNameEn");
          final int _cursorIndexOfBadgeNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "badgeNameKn");
          final int _cursorIndexOfDistrictId = CursorUtil.getColumnIndexOrThrow(_cursor, "districtId");
          final int _cursorIndexOfDistrictName = CursorUtil.getColumnIndexOrThrow(_cursor, "districtName");
          final int _cursorIndexOfDistrictNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "districtNameKn");
          final int _cursorIndexOfDistrictColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "districtColorHex");
          final int _cursorIndexOfHeroEmoji = CursorUtil.getColumnIndexOrThrow(_cursor, "heroEmoji");
          final int _cursorIndexOfEarnedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "earnedDate");
          final List<BadgeEntity> _result = new ArrayList<BadgeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BadgeEntity _item;
            final String _tmpBadgeId;
            _tmpBadgeId = _cursor.getString(_cursorIndexOfBadgeId);
            final String _tmpHeroId;
            _tmpHeroId = _cursor.getString(_cursorIndexOfHeroId);
            final String _tmpHeroNameEn;
            _tmpHeroNameEn = _cursor.getString(_cursorIndexOfHeroNameEn);
            final String _tmpHeroNameKn;
            _tmpHeroNameKn = _cursor.getString(_cursorIndexOfHeroNameKn);
            final String _tmpBadgeNameEn;
            _tmpBadgeNameEn = _cursor.getString(_cursorIndexOfBadgeNameEn);
            final String _tmpBadgeNameKn;
            _tmpBadgeNameKn = _cursor.getString(_cursorIndexOfBadgeNameKn);
            final String _tmpDistrictId;
            _tmpDistrictId = _cursor.getString(_cursorIndexOfDistrictId);
            final String _tmpDistrictName;
            _tmpDistrictName = _cursor.getString(_cursorIndexOfDistrictName);
            final String _tmpDistrictNameKn;
            _tmpDistrictNameKn = _cursor.getString(_cursorIndexOfDistrictNameKn);
            final String _tmpDistrictColorHex;
            _tmpDistrictColorHex = _cursor.getString(_cursorIndexOfDistrictColorHex);
            final String _tmpHeroEmoji;
            _tmpHeroEmoji = _cursor.getString(_cursorIndexOfHeroEmoji);
            final long _tmpEarnedDate;
            _tmpEarnedDate = _cursor.getLong(_cursorIndexOfEarnedDate);
            _item = new BadgeEntity(_tmpBadgeId,_tmpHeroId,_tmpHeroNameEn,_tmpHeroNameKn,_tmpBadgeNameEn,_tmpBadgeNameKn,_tmpDistrictId,_tmpDistrictName,_tmpDistrictNameKn,_tmpDistrictColorHex,_tmpHeroEmoji,_tmpEarnedDate);
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
  public Object getAllBadgesSync(final Continuation<? super List<BadgeEntity>> $completion) {
    final String _sql = "SELECT * FROM badges ORDER BY earnedDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<BadgeEntity>>() {
      @Override
      @NonNull
      public List<BadgeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfBadgeId = CursorUtil.getColumnIndexOrThrow(_cursor, "badgeId");
          final int _cursorIndexOfHeroId = CursorUtil.getColumnIndexOrThrow(_cursor, "heroId");
          final int _cursorIndexOfHeroNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "heroNameEn");
          final int _cursorIndexOfHeroNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "heroNameKn");
          final int _cursorIndexOfBadgeNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "badgeNameEn");
          final int _cursorIndexOfBadgeNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "badgeNameKn");
          final int _cursorIndexOfDistrictId = CursorUtil.getColumnIndexOrThrow(_cursor, "districtId");
          final int _cursorIndexOfDistrictName = CursorUtil.getColumnIndexOrThrow(_cursor, "districtName");
          final int _cursorIndexOfDistrictNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "districtNameKn");
          final int _cursorIndexOfDistrictColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "districtColorHex");
          final int _cursorIndexOfHeroEmoji = CursorUtil.getColumnIndexOrThrow(_cursor, "heroEmoji");
          final int _cursorIndexOfEarnedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "earnedDate");
          final List<BadgeEntity> _result = new ArrayList<BadgeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BadgeEntity _item;
            final String _tmpBadgeId;
            _tmpBadgeId = _cursor.getString(_cursorIndexOfBadgeId);
            final String _tmpHeroId;
            _tmpHeroId = _cursor.getString(_cursorIndexOfHeroId);
            final String _tmpHeroNameEn;
            _tmpHeroNameEn = _cursor.getString(_cursorIndexOfHeroNameEn);
            final String _tmpHeroNameKn;
            _tmpHeroNameKn = _cursor.getString(_cursorIndexOfHeroNameKn);
            final String _tmpBadgeNameEn;
            _tmpBadgeNameEn = _cursor.getString(_cursorIndexOfBadgeNameEn);
            final String _tmpBadgeNameKn;
            _tmpBadgeNameKn = _cursor.getString(_cursorIndexOfBadgeNameKn);
            final String _tmpDistrictId;
            _tmpDistrictId = _cursor.getString(_cursorIndexOfDistrictId);
            final String _tmpDistrictName;
            _tmpDistrictName = _cursor.getString(_cursorIndexOfDistrictName);
            final String _tmpDistrictNameKn;
            _tmpDistrictNameKn = _cursor.getString(_cursorIndexOfDistrictNameKn);
            final String _tmpDistrictColorHex;
            _tmpDistrictColorHex = _cursor.getString(_cursorIndexOfDistrictColorHex);
            final String _tmpHeroEmoji;
            _tmpHeroEmoji = _cursor.getString(_cursorIndexOfHeroEmoji);
            final long _tmpEarnedDate;
            _tmpEarnedDate = _cursor.getLong(_cursorIndexOfEarnedDate);
            _item = new BadgeEntity(_tmpBadgeId,_tmpHeroId,_tmpHeroNameEn,_tmpHeroNameKn,_tmpBadgeNameEn,_tmpBadgeNameKn,_tmpDistrictId,_tmpDistrictName,_tmpDistrictNameKn,_tmpDistrictColorHex,_tmpHeroEmoji,_tmpEarnedDate);
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
  public Object hasBadge(final String badgeId, final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM badges WHERE badgeId = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, badgeId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp != 0;
          } else {
            _result = false;
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
  public LiveData<Integer> getBadgeCount() {
    final String _sql = "SELECT COUNT(*) FROM badges";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"badges"}, false, new Callable<Integer>() {
      @Override
      @Nullable
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

  @Override
  public LiveData<List<BadgeEntity>> getBadgesByDistrict(final String districtId) {
    final String _sql = "SELECT * FROM badges WHERE districtId = ? ORDER BY earnedDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, districtId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"badges"}, false, new Callable<List<BadgeEntity>>() {
      @Override
      @Nullable
      public List<BadgeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfBadgeId = CursorUtil.getColumnIndexOrThrow(_cursor, "badgeId");
          final int _cursorIndexOfHeroId = CursorUtil.getColumnIndexOrThrow(_cursor, "heroId");
          final int _cursorIndexOfHeroNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "heroNameEn");
          final int _cursorIndexOfHeroNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "heroNameKn");
          final int _cursorIndexOfBadgeNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "badgeNameEn");
          final int _cursorIndexOfBadgeNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "badgeNameKn");
          final int _cursorIndexOfDistrictId = CursorUtil.getColumnIndexOrThrow(_cursor, "districtId");
          final int _cursorIndexOfDistrictName = CursorUtil.getColumnIndexOrThrow(_cursor, "districtName");
          final int _cursorIndexOfDistrictNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "districtNameKn");
          final int _cursorIndexOfDistrictColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "districtColorHex");
          final int _cursorIndexOfHeroEmoji = CursorUtil.getColumnIndexOrThrow(_cursor, "heroEmoji");
          final int _cursorIndexOfEarnedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "earnedDate");
          final List<BadgeEntity> _result = new ArrayList<BadgeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BadgeEntity _item;
            final String _tmpBadgeId;
            _tmpBadgeId = _cursor.getString(_cursorIndexOfBadgeId);
            final String _tmpHeroId;
            _tmpHeroId = _cursor.getString(_cursorIndexOfHeroId);
            final String _tmpHeroNameEn;
            _tmpHeroNameEn = _cursor.getString(_cursorIndexOfHeroNameEn);
            final String _tmpHeroNameKn;
            _tmpHeroNameKn = _cursor.getString(_cursorIndexOfHeroNameKn);
            final String _tmpBadgeNameEn;
            _tmpBadgeNameEn = _cursor.getString(_cursorIndexOfBadgeNameEn);
            final String _tmpBadgeNameKn;
            _tmpBadgeNameKn = _cursor.getString(_cursorIndexOfBadgeNameKn);
            final String _tmpDistrictId;
            _tmpDistrictId = _cursor.getString(_cursorIndexOfDistrictId);
            final String _tmpDistrictName;
            _tmpDistrictName = _cursor.getString(_cursorIndexOfDistrictName);
            final String _tmpDistrictNameKn;
            _tmpDistrictNameKn = _cursor.getString(_cursorIndexOfDistrictNameKn);
            final String _tmpDistrictColorHex;
            _tmpDistrictColorHex = _cursor.getString(_cursorIndexOfDistrictColorHex);
            final String _tmpHeroEmoji;
            _tmpHeroEmoji = _cursor.getString(_cursorIndexOfHeroEmoji);
            final long _tmpEarnedDate;
            _tmpEarnedDate = _cursor.getLong(_cursorIndexOfEarnedDate);
            _item = new BadgeEntity(_tmpBadgeId,_tmpHeroId,_tmpHeroNameEn,_tmpHeroNameKn,_tmpBadgeNameEn,_tmpBadgeNameKn,_tmpDistrictId,_tmpDistrictName,_tmpDistrictNameKn,_tmpDistrictColorHex,_tmpHeroEmoji,_tmpEarnedDate);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
