package com.nammakathey.app.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.nammakathey.app.data.model.BookmarkEntity;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
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
public final class BookmarkDao_Impl implements BookmarkDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BookmarkEntity> __insertionAdapterOfBookmarkEntity;

  private final EntityDeletionOrUpdateAdapter<BookmarkEntity> __deletionAdapterOfBookmarkEntity;

  private final SharedSQLiteStatement __preparedStmtOfRemoveBookmark;

  public BookmarkDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBookmarkEntity = new EntityInsertionAdapter<BookmarkEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `bookmarks` (`heroId`,`heroNameEn`,`heroNameKn`,`districtId`,`districtName`,`colorHex`,`emoji`,`savedAt`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BookmarkEntity entity) {
        statement.bindString(1, entity.getHeroId());
        statement.bindString(2, entity.getHeroNameEn());
        statement.bindString(3, entity.getHeroNameKn());
        statement.bindString(4, entity.getDistrictId());
        statement.bindString(5, entity.getDistrictName());
        statement.bindString(6, entity.getColorHex());
        statement.bindString(7, entity.getEmoji());
        statement.bindLong(8, entity.getSavedAt());
      }
    };
    this.__deletionAdapterOfBookmarkEntity = new EntityDeletionOrUpdateAdapter<BookmarkEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `bookmarks` WHERE `heroId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BookmarkEntity entity) {
        statement.bindString(1, entity.getHeroId());
      }
    };
    this.__preparedStmtOfRemoveBookmark = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM bookmarks WHERE heroId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertBookmark(final BookmarkEntity bookmark,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBookmarkEntity.insert(bookmark);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteBookmark(final BookmarkEntity bookmark,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBookmarkEntity.handle(bookmark);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object removeBookmark(final String heroId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfRemoveBookmark.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, heroId);
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
          __preparedStmtOfRemoveBookmark.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<BookmarkEntity>> getAllBookmarks() {
    final String _sql = "SELECT * FROM bookmarks ORDER BY savedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"bookmarks"}, false, new Callable<List<BookmarkEntity>>() {
      @Override
      @Nullable
      public List<BookmarkEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfHeroId = CursorUtil.getColumnIndexOrThrow(_cursor, "heroId");
          final int _cursorIndexOfHeroNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "heroNameEn");
          final int _cursorIndexOfHeroNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "heroNameKn");
          final int _cursorIndexOfDistrictId = CursorUtil.getColumnIndexOrThrow(_cursor, "districtId");
          final int _cursorIndexOfDistrictName = CursorUtil.getColumnIndexOrThrow(_cursor, "districtName");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfEmoji = CursorUtil.getColumnIndexOrThrow(_cursor, "emoji");
          final int _cursorIndexOfSavedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "savedAt");
          final List<BookmarkEntity> _result = new ArrayList<BookmarkEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BookmarkEntity _item;
            final String _tmpHeroId;
            _tmpHeroId = _cursor.getString(_cursorIndexOfHeroId);
            final String _tmpHeroNameEn;
            _tmpHeroNameEn = _cursor.getString(_cursorIndexOfHeroNameEn);
            final String _tmpHeroNameKn;
            _tmpHeroNameKn = _cursor.getString(_cursorIndexOfHeroNameKn);
            final String _tmpDistrictId;
            _tmpDistrictId = _cursor.getString(_cursorIndexOfDistrictId);
            final String _tmpDistrictName;
            _tmpDistrictName = _cursor.getString(_cursorIndexOfDistrictName);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            final String _tmpEmoji;
            _tmpEmoji = _cursor.getString(_cursorIndexOfEmoji);
            final long _tmpSavedAt;
            _tmpSavedAt = _cursor.getLong(_cursorIndexOfSavedAt);
            _item = new BookmarkEntity(_tmpHeroId,_tmpHeroNameEn,_tmpHeroNameKn,_tmpDistrictId,_tmpDistrictName,_tmpColorHex,_tmpEmoji,_tmpSavedAt);
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
  public Object isBookmarked(final String heroId, final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM bookmarks WHERE heroId = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, heroId);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
