package com.nammakathey.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.nammakathey.app.data.model.*
import com.nammakathey.app.data.repository.HeroRepository
import com.nammakathey.app.util.AppPreferences
import com.nammakathey.app.util.LanguageManager
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val repo = HeroRepository(application)
    val prefs = AppPreferences(application)

    // Language — kept in sync with LanguageManager
    private val _isKannada = MutableLiveData(LanguageManager.isKannada)
    val isKannada: LiveData<Boolean> = _isKannada

    fun toggleLanguage() {
        LanguageManager.toggle()
        _isKannada.value = LanguageManager.isKannada
    }

    fun setKannada(value: Boolean) {
        LanguageManager.set(value)
        _isKannada.value = value
    }

    // Districts
    private val _districts = MutableLiveData<List<District>>()
    val districts: LiveData<List<District>> = _districts

    private val _selectedDistrict = MutableLiveData<District?>()
    val selectedDistrict: LiveData<District?> = _selectedDistrict

    fun loadDistricts() { _districts.value = repo.getAllDistricts() }
    fun selectDistrict(district: District) { _selectedDistrict.value = district }

    // Heroes
    private val _selectedHero = MutableLiveData<Hero?>()
    val selectedHero: LiveData<Hero?> = _selectedHero
    fun selectHero(hero: Hero) { _selectedHero.value = hero }

    // Search
    private val _searchResults = MutableLiveData<List<Pair<Hero, District>>>()
    val searchResults: LiveData<List<Pair<Hero, District>>> = _searchResults

    fun search(query: String) {
        _searchResults.value = if (query.isBlank()) emptyList()
        else repo.searchHeroes(query, LanguageManager.isKannada)
    }

    // Room LiveData
    val allBadges = repo.getAllBadges()
    val badgeCount = repo.getBadgeCount()
    val allBookmarks = repo.getAllBookmarks()

    // Actions
    fun earnBadge(hero: Hero, district: District) = viewModelScope.launch { repo.earnBadge(hero, district) }
    suspend fun hasBadge(badgeId: String) = repo.hasBadge(badgeId)

    fun toggleBookmark(hero: Hero, district: District, callback: (Boolean) -> Unit) =
        viewModelScope.launch { callback(repo.toggleBookmark(hero, district)) }

    suspend fun isBookmarked(heroId: String) = repo.isBookmarked(heroId)

    fun markStoryRead(heroId: String, districtId: String) =
        viewModelScope.launch { repo.markStoryRead(heroId, districtId) }

    fun markQuizCompleted(heroId: String, districtId: String) =
        viewModelScope.launch { repo.markQuizCompleted(heroId, districtId) }

    private val _districtCompletion = MutableLiveData<Pair<Int, Int>>()
    val districtCompletion: LiveData<Pair<Int, Int>> = _districtCompletion

    fun loadDistrictCompletion(districtId: String) = viewModelScope.launch {
        _districtCompletion.value = repo.getDistrictCompletion(districtId)
    }
}
