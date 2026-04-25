package com.nammakathey.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class HeroesData(val districts: List<District>)

@Parcelize
data class District(
    val districtId: String,
    val districtName: String,
    val districtNameKn: String,
    val colorHex: String,
    val latitude: Double,
    val longitude: Double,
    val heroes: List<Hero>
) : Parcelable

@Parcelize
data class Hero(
    val heroId: String,
    val nameEn: String,
    val nameKn: String,
    val era: String,
    val category: String,
    val categoryKn: String,
    val taglineEn: String,
    val taglineKn: String,
    val imageEmoji: String,
    val colorHex: String,
    val storyPages: List<StoryPage>,
    val quiz: List<QuizQuestion>,
    val badgeId: String,
    val badgeNameEn: String,
    val badgeNameKn: String,
    val statueLocation: StatueLocation?
) : Parcelable {
    fun displayName(isKannada: Boolean) = if (isKannada) nameKn else nameEn
    fun displayTagline(isKannada: Boolean) = if (isKannada) taglineKn else taglineEn
    fun displayCategory(isKannada: Boolean) = if (isKannada) categoryKn else category
    fun displayBadgeName(isKannada: Boolean) = if (isKannada) badgeNameKn else badgeNameEn
}

@Parcelize
data class StoryPage(
    val pageNumber: Int,
    val titleEn: String,
    val titleKn: String,
    val contentEn: String,
    val contentKn: String
) : Parcelable {
    fun displayTitle(isKannada: Boolean) = if (isKannada) titleKn else titleEn
    fun displayContent(isKannada: Boolean) = if (isKannada) contentKn else contentEn
}

@Parcelize
data class QuizQuestion(
    val questionEn: String,
    val questionKn: String,
    val options: List<String>,
    val optionsKn: List<String>,
    val correctIndex: Int
) : Parcelable {
    fun displayQuestion(isKannada: Boolean) = if (isKannada) questionKn else questionEn
    fun displayOptions(isKannada: Boolean) = if (isKannada) optionsKn else options
}

@Parcelize
data class StatueLocation(
    val nameEn: String,
    val nameKn: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    // Optional Wikipedia URL — null means no Wikipedia button shown
    val wikipediaUrl: String? = null
) : Parcelable {
    fun displayName(isKannada: Boolean) = if (isKannada) nameKn else nameEn

    /** Google Maps deep-link using coordinates */
    fun mapsUrl(): String =
        "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"

    /** Wikipedia URL if provided */
    fun wikiUrl(): String? = wikipediaUrl
}
