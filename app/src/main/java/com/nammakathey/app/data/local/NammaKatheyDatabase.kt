package com.nammakathey.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nammakathey.app.data.model.BadgeEntity
import com.nammakathey.app.data.model.BookmarkEntity
import com.nammakathey.app.data.model.ProgressEntity

@Database(
    entities = [BadgeEntity::class, BookmarkEntity::class, ProgressEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NammaKatheyDatabase : RoomDatabase() {
    abstract fun badgeDao(): BadgeDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun progressDao(): ProgressDao

    companion object {
        @Volatile private var INSTANCE: NammaKatheyDatabase? = null

        fun getDatabase(context: Context): NammaKatheyDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    NammaKatheyDatabase::class.java,
                    "namma_kathey_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
