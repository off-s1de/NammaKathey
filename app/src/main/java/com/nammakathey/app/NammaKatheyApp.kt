package com.nammakathey.app

import android.app.Application
import com.nammakathey.app.util.LanguageManager

class NammaKatheyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LanguageManager.init(this)
    }
}
