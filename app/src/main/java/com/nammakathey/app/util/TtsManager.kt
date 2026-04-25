package com.nammakathey.app.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.*

class TtsManager(context: Context) {

    private var tts: TextToSpeech? = null
    private var isReady = false
    private var isSpeaking = false

    var onSpeakingStateChanged: ((Boolean) -> Unit)? = null

    init {
        tts = TextToSpeech(context) { status ->
            isReady = status == TextToSpeech.SUCCESS
        }

        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                isSpeaking = true
                onSpeakingStateChanged?.invoke(true)
            }
            override fun onDone(utteranceId: String?) {
                isSpeaking = false
                onSpeakingStateChanged?.invoke(false)
            }
            @Deprecated("Deprecated")
            override fun onError(utteranceId: String?) {
                isSpeaking = false
                onSpeakingStateChanged?.invoke(false)
            }
        })
    }

    fun speak(text: String, isKannada: Boolean) {
        if (!isReady) return
        val locale = if (isKannada) Locale("kn", "IN") else Locale.ENGLISH

        // Try Kannada locale; fall back to English if unavailable
        val result = tts?.setLanguage(locale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            tts?.setLanguage(Locale.ENGLISH)
        }

        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "namma_tts_${System.currentTimeMillis()}")
    }

    fun stop() {
        tts?.stop()
        isSpeaking = false
        onSpeakingStateChanged?.invoke(false)
    }

    fun toggle(text: String, isKannada: Boolean) {
        if (isSpeaking) stop() else speak(text, isKannada)
    }

    fun isCurrentlySpeaking() = isSpeaking

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
