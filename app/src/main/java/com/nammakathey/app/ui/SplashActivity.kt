package com.nammakathey.app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nammakathey.app.databinding.ActivitySplashBinding
import com.nammakathey.app.util.AppPreferences

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val prefs by lazy { AppPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Animate logo
        binding.logoEmoji.animate()
            .scaleX(1.15f).scaleY(1.15f)
            .setDuration(600)
            .withEndAction {
                binding.logoEmoji.animate()
                    .scaleX(1f).scaleY(1f)
                    .setDuration(400)
                    .start()
            }.start()

        binding.appNameText.alpha = 0f
        binding.appNameText.animate().alpha(1f).setStartDelay(300).setDuration(600).start()
        binding.taglineText.alpha = 0f
        binding.taglineText.animate().alpha(1f).setStartDelay(600).setDuration(600).start()

        Handler(Looper.getMainLooper()).postDelayed({
            val next = if (prefs.onboardingCompleted) MainActivity::class.java
                       else OnboardingActivity::class.java
            startActivity(Intent(this, next))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 2200)
    }
}
