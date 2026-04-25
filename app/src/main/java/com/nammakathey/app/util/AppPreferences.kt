package com.nammakathey.app.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AppPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("namma_kathey_prefs", Context.MODE_PRIVATE)

    var isKannada: Boolean
        get() = prefs.getBoolean(KEY_LANGUAGE, false)
        set(value) = prefs.edit { putBoolean(KEY_LANGUAGE, value) }

    var onboardingCompleted: Boolean
        get() = prefs.getBoolean(KEY_ONBOARDING, false)
        set(value) = prefs.edit { putBoolean(KEY_ONBOARDING, value) }

    var selectedDistrictId: String?
        get() = prefs.getString(KEY_SELECTED_DISTRICT, null)
        set(value) = prefs.edit { putString(KEY_SELECTED_DISTRICT, value) }

    companion object {
        private const val KEY_LANGUAGE = "language_kannada"
        private const val KEY_ONBOARDING = "onboarding_done"
        private const val KEY_SELECTED_DISTRICT = "selected_district"
    }
}
