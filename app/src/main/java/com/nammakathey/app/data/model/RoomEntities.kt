package com.nammakathey.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "badges")
data class BadgeEntity(
    @PrimaryKey val badgeId: String,
    val heroId: String,
    val heroNameEn: String,
    val heroNameKn: String,
    val badgeNameEn: String,
    val badgeNameKn: String,
    val districtId: String,
    val districtName: String,
    val districtNameKn: String,
    val districtColorHex: String,
    val heroEmoji: String,
    val earnedDate: Long = System.currentTimeMillis()
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val heroId: String,
    val heroNameEn: String,
    val heroNameKn: String,
    val districtId: String,
    val districtName: String,
    val colorHex: String,
    val emoji: String,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "progress")
data class ProgressEntity(
    @PrimaryKey val heroId: String,
    val districtId: String,
    val storiesRead: Int = 0,
    val quizCompleted: Boolean = false,
    val lastReadAt: Long = System.currentTimeMillis()
)
