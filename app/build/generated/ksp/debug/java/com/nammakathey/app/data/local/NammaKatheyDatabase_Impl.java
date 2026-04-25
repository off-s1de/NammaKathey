package com.nammakathey.app.data.local;

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
public final class NammaKatheyDatabase_Impl extends NammaKatheyDatabase {
  private volatile BadgeDao _badgeDao;

  private volatile BookmarkDao _bookmarkDao;

  private volatile ProgressDao _progressDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `badges` (`badgeId` TEXT NOT NULL, `heroId` TEXT NOT NULL, `heroNameEn` TEXT NOT NULL, `heroNameKn` TEXT NOT NULL, `badgeNameEn` TEXT NOT NULL, `badgeNameKn` TEXT NOT NULL, `districtId` TEXT NOT NULL, `districtName` TEXT NOT NULL, `districtNameKn` TEXT NOT NULL, `districtColorHex` TEXT NOT NULL, `heroEmoji` TEXT NOT NULL, `earnedDate` INTEGER NOT NULL, PRIMARY KEY(`badgeId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `bookmarks` (`heroId` TEXT NOT NULL, `heroNameEn` TEXT NOT NULL, `heroNameKn` TEXT NOT NULL, `districtId` TEXT NOT NULL, `districtName` TEXT NOT NULL, `colorHex` TEXT NOT NULL, `emoji` TEXT NOT NULL, `savedAt` INTEGER NOT NULL, PRIMARY KEY(`heroId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `progress` (`heroId` TEXT NOT NULL, `districtId` TEXT NOT NULL, `storiesRead` INTEGER NOT NULL, `quizCompleted` INTEGER NOT NULL, `lastReadAt` INTEGER NOT NULL, PRIMARY KEY(`heroId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'e5a24d8a10a4467087dd762c83c3e0f3')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `badges`");
        db.execSQL("DROP TABLE IF EXISTS `bookmarks`");
        db.execSQL("DROP TABLE IF EXISTS `progress`");
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
        final HashMap<String, TableInfo.Column> _columnsBadges = new HashMap<String, TableInfo.Column>(12);
        _columnsBadges.put("badgeId", new TableInfo.Column("badgeId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("heroId", new TableInfo.Column("heroId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("heroNameEn", new TableInfo.Column("heroNameEn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("heroNameKn", new TableInfo.Column("heroNameKn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("badgeNameEn", new TableInfo.Column("badgeNameEn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("badgeNameKn", new TableInfo.Column("badgeNameKn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("districtId", new TableInfo.Column("districtId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("districtName", new TableInfo.Column("districtName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("districtNameKn", new TableInfo.Column("districtNameKn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("districtColorHex", new TableInfo.Column("districtColorHex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("heroEmoji", new TableInfo.Column("heroEmoji", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("earnedDate", new TableInfo.Column("earnedDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBadges = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBadges = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBadges = new TableInfo("badges", _columnsBadges, _foreignKeysBadges, _indicesBadges);
        final TableInfo _existingBadges = TableInfo.read(db, "badges");
        if (!_infoBadges.equals(_existingBadges)) {
          return new RoomOpenHelper.ValidationResult(false, "badges(com.nammakathey.app.data.model.BadgeEntity).\n"
                  + " Expected:\n" + _infoBadges + "\n"
                  + " Found:\n" + _existingBadges);
        }
        final HashMap<String, TableInfo.Column> _columnsBookmarks = new HashMap<String, TableInfo.Column>(8);
        _columnsBookmarks.put("heroId", new TableInfo.Column("heroId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("heroNameEn", new TableInfo.Column("heroNameEn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("heroNameKn", new TableInfo.Column("heroNameKn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("districtId", new TableInfo.Column("districtId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("districtName", new TableInfo.Column("districtName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("colorHex", new TableInfo.Column("colorHex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("emoji", new TableInfo.Column("emoji", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("savedAt", new TableInfo.Column("savedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBookmarks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBookmarks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBookmarks = new TableInfo("bookmarks", _columnsBookmarks, _foreignKeysBookmarks, _indicesBookmarks);
        final TableInfo _existingBookmarks = TableInfo.read(db, "bookmarks");
        if (!_infoBookmarks.equals(_existingBookmarks)) {
          return new RoomOpenHelper.ValidationResult(false, "bookmarks(com.nammakathey.app.data.model.BookmarkEntity).\n"
                  + " Expected:\n" + _infoBookmarks + "\n"
                  + " Found:\n" + _existingBookmarks);
        }
        final HashMap<String, TableInfo.Column> _columnsProgress = new HashMap<String, TableInfo.Column>(5);
        _columnsProgress.put("heroId", new TableInfo.Column("heroId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgress.put("districtId", new TableInfo.Column("districtId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgress.put("storiesRead", new TableInfo.Column("storiesRead", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgress.put("quizCompleted", new TableInfo.Column("quizCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgress.put("lastReadAt", new TableInfo.Column("lastReadAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProgress = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProgress = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProgress = new TableInfo("progress", _columnsProgress, _foreignKeysProgress, _indicesProgress);
        final TableInfo _existingProgress = TableInfo.read(db, "progress");
        if (!_infoProgress.equals(_existingProgress)) {
          return new RoomOpenHelper.ValidationResult(false, "progress(com.nammakathey.app.data.model.ProgressEntity).\n"
                  + " Expected:\n" + _infoProgress + "\n"
                  + " Found:\n" + _existingProgress);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "e5a24d8a10a4467087dd762c83c3e0f3", "770e046b42c74f5fd9469cfb507a6f0b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "badges","bookmarks","progress");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `badges`");
      _db.execSQL("DELETE FROM `bookmarks`");
      _db.execSQL("DELETE FROM `progress`");
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
    _typeConvertersMap.put(BadgeDao.class, BadgeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BookmarkDao.class, BookmarkDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProgressDao.class, ProgressDao_Impl.getRequiredConverters());
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
  public BadgeDao badgeDao() {
    if (_badgeDao != null) {
      return _badgeDao;
    } else {
      synchronized(this) {
        if(_badgeDao == null) {
          _badgeDao = new BadgeDao_Impl(this);
        }
        return _badgeDao;
      }
    }
  }

  @Override
  public BookmarkDao bookmarkDao() {
    if (_bookmarkDao != null) {
      return _bookmarkDao;
    } else {
      synchronized(this) {
        if(_bookmarkDao == null) {
          _bookmarkDao = new BookmarkDao_Impl(this);
        }
        return _bookmarkDao;
      }
    }
  }

  @Override
  public ProgressDao progressDao() {
    if (_progressDao != null) {
      return _progressDao;
    } else {
      synchronized(this) {
        if(_progressDao == null) {
          _progressDao = new ProgressDao_Impl(this);
        }
        return _progressDao;
      }
    }
  }
}
