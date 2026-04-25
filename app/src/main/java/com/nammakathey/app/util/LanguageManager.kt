package com.nammakathey.app.util

import android.content.Context

object LanguageManager {

    private val listeners = mutableListOf<() -> Unit>()
    private lateinit var prefs: AppPreferences

    fun init(context: Context) {
        if (!::prefs.isInitialized) {
            prefs = AppPreferences(context.applicationContext)
        }
    }

    val isKannada: Boolean get() = prefs.isKannada

    fun toggle() {
        prefs.isKannada = !prefs.isKannada
        broadcast()
    }

    fun set(kannada: Boolean) {
        prefs.isKannada = kannada
        broadcast()
    }

    fun register(listener: () -> Unit) {
        if (!listeners.contains(listener)) listeners.add(listener)
    }

    fun unregister(listener: () -> Unit) {
        listeners.remove(listener)
    }

    private fun broadcast() {
        listeners.toList().forEach { it.invoke() }
    }

    fun str(en: String, kn: String) = if (isKannada) kn else en
}
