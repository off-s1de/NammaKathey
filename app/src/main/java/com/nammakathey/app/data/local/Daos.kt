package com.nammakathey.app.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nammakathey.app.data.model.BadgeEntity
import com.nammakathey.app.data.model.BookmarkEntity
import com.nammakathey.app.data.model.ProgressEntity

@Dao
interface BadgeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBadge(badge: BadgeEntity)

    @Query("SELECT * FROM badges ORDER BY earnedDate DESC")
    fun getAllBadges(): LiveData<List<BadgeEntity>>

    @Query("SELECT * FROM badges ORDER BY earnedDate DESC")
    suspend fun getAllBadgesSync(): List<BadgeEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM badges WHERE badgeId = :badgeId)")
    suspend fun hasBadge(badgeId: String): Boolean

    @Query("SELECT COUNT(*) FROM badges")
    fun getBadgeCount(): LiveData<Int>

    @Query("SELECT * FROM badges WHERE districtId = :districtId ORDER BY earnedDate DESC")
    fun getBadgesByDistrict(districtId: String): LiveData<List<BadgeEntity>>
}

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE heroId = :heroId")
    suspend fun removeBookmark(heroId: String)

    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    fun getAllBookmarks(): LiveData<List<BookmarkEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE heroId = :heroId)")
    suspend fun isBookmarked(heroId: String): Boolean
}

@Dao
interface ProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(progress: ProgressEntity)

    @Query("SELECT * FROM progress WHERE heroId = :heroId")
    suspend fun getProgress(heroId: String): ProgressEntity?

    @Query("SELECT * FROM progress WHERE districtId = :districtId")
    suspend fun getDistrictProgress(districtId: String): List<ProgressEntity>

    @Query("SELECT COUNT(*) FROM progress WHERE districtId = :districtId AND quizCompleted = 1")
    suspend fun getCompletedCountForDistrict(districtId: String): Int
}
