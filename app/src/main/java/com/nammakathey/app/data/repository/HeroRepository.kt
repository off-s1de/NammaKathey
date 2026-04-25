package com.nammakathey.app.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.nammakathey.app.data.local.NammaKatheyDatabase
import com.nammakathey.app.data.model.*

class HeroRepository(private val context: Context) {

    private val db = NammaKatheyDatabase.getDatabase(context)
    private val badgeDao = db.badgeDao()
    private val bookmarkDao = db.bookmarkDao()
    private val progressDao = db.progressDao()

    // ─── JSON Data ────────────────────────────────────────────────────────────

    private var _cachedData: HeroesData? = null

    fun getHeroesData(): HeroesData {
        if (_cachedData == null) {
            val json = context.assets.open("data/heroes.json")
                .bufferedReader().use { it.readText() }
            _cachedData = Gson().fromJson(json, HeroesData::class.java)
        }
        return _cachedData!!
    }

    fun getAllDistricts(): List<District> = getHeroesData().districts

    fun getDistrict(districtId: String): District? =
        getAllDistricts().find { it.districtId == districtId }

    fun getHero(heroId: String): Hero? =
        getAllDistricts().flatMap { it.heroes }.find { it.heroId == heroId }

    fun searchHeroes(query: String, isKannada: Boolean): List<Pair<Hero, District>> {
        val q = query.trim().lowercase()
        return getAllDistricts().flatMap { district ->
            district.heroes
                .filter { hero ->
                    hero.nameEn.lowercase().contains(q) ||
                    hero.nameKn.contains(q) ||
                    hero.category.lowercase().contains(q) ||
                    district.districtName.lowercase().contains(q)
                }
                .map { Pair(it, district) }
        }
    }

    fun getAllStatueLocations(): List<Pair<StatueLocation, Hero>> =
        getAllDistricts().flatMap { d -> d.heroes.mapNotNull { h -> h.statueLocation?.let { Pair(it, h) } } }

    // ─── Badges (Room) ────────────────────────────────────────────────────────

    fun getAllBadges(): LiveData<List<BadgeEntity>> = badgeDao.getAllBadges()
    fun getBadgeCount(): LiveData<Int> = badgeDao.getBadgeCount()

    suspend fun earnBadge(hero: Hero, district: District) {
        if (!badgeDao.hasBadge(hero.badgeId)) {
            badgeDao.insertBadge(
                BadgeEntity(
                    badgeId = hero.badgeId,
                    heroId = hero.heroId,
                    heroNameEn = hero.nameEn,
                    heroNameKn = hero.nameKn,
                    badgeNameEn = hero.badgeNameEn,
                    badgeNameKn = hero.badgeNameKn,
                    districtId = district.districtId,
                    districtName = district.districtName,
                    districtNameKn = district.districtNameKn,
                    districtColorHex = district.colorHex,
                    heroEmoji = hero.imageEmoji
                )
            )
        }
    }

    suspend fun hasBadge(badgeId: String) = badgeDao.hasBadge(badgeId)

    // ─── Bookmarks (Room) ─────────────────────────────────────────────────────

    fun getAllBookmarks(): LiveData<List<BookmarkEntity>> = bookmarkDao.getAllBookmarks()

    suspend fun toggleBookmark(hero: Hero, district: District): Boolean {
        return if (bookmarkDao.isBookmarked(hero.heroId)) {
            bookmarkDao.removeBookmark(hero.heroId)
            false
        } else {
            bookmarkDao.insertBookmark(
                BookmarkEntity(
                    heroId = hero.heroId,
                    heroNameEn = hero.nameEn,
                    heroNameKn = hero.nameKn,
                    districtId = district.districtId,
                    districtName = district.districtName,
                    colorHex = district.colorHex,
                    emoji = hero.imageEmoji
                )
            )
            true
        }
    }

    suspend fun isBookmarked(heroId: String) = bookmarkDao.isBookmarked(heroId)

    // ─── Progress (Room) ──────────────────────────────────────────────────────

    suspend fun markStoryRead(heroId: String, districtId: String) {
        val existing = progressDao.getProgress(heroId)
        progressDao.upsertProgress(
            ProgressEntity(
                heroId = heroId,
                districtId = districtId,
                storiesRead = (existing?.storiesRead ?: 0) + 1,
                quizCompleted = existing?.quizCompleted ?: false
            )
        )
    }

    suspend fun markQuizCompleted(heroId: String, districtId: String) {
        val existing = progressDao.getProgress(heroId)
        progressDao.upsertProgress(
            ProgressEntity(
                heroId = heroId,
                districtId = districtId,
                storiesRead = existing?.storiesRead ?: 0,
                quizCompleted = true
            )
        )
    }

    suspend fun getDistrictCompletion(districtId: String): Pair<Int, Int> {
        val district = getDistrict(districtId) ?: return Pair(0, 0)
        val total = district.heroes.size
        val completed = progressDao.getCompletedCountForDistrict(districtId)
        return Pair(completed, total)
    }
}
