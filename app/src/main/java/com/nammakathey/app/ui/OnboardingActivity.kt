package com.nammakathey.app.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.nammakathey.app.databinding.ActivityOnboardingBinding
import com.nammakathey.app.ui.adapters.OnboardingAdapter
import com.nammakathey.app.util.AppPreferences
import com.nammakathey.app.util.LanguageManager

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private val prefs by lazy { AppPreferences(this) }
    private var selectedKannada = false
    private val dotViews = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pages = listOf(
            OnboardingPage("🗺️", "Discover Karnataka's Heroes", "ಕರ್ನಾಟಕದ ವೀರರನ್ನು ಅನ್ವೇಷಿಸಿ",
                "Tap any district on the interactive map to explore its legendary heroes",
                "ಸಂವಾದಾತ್ಮಕ ನಕ್ಷೆಯಲ್ಲಿ ಜಿಲ್ಲೆ ಟ್ಯಾಪ್ ಮಾಡಿ ವೀರರ ಕಥೆ ಅನ್ವೇಷಿಸಿ"),
            OnboardingPage("📖", "Read Their Stories", "ಅವರ ಕಥೆಗಳನ್ನು ಓದಿ",
                "Swipe through illustrated stories in both Kannada and English",
                "ಕನ್ನಡ ಮತ್ತು ಇಂಗ್ಲಿಷ್‌ನಲ್ಲಿ ಕಥೆಗಳನ್ನು ಓದಿ"),
            OnboardingPage("🏅", "Earn Heritage Badges", "ಪರಂಪರೆ ಬ್ಯಾಡ್ಜ್ ಗಳಿಸಿ",
                "Complete quizzes to earn Heritage Badges and unlock Karnataka Mega Quiz",
                "ಪ್ರಶ್ನೋತ್ತರಿ ಮಾಡಿ ಬ್ಯಾಡ್ಜ್ ಗಳಿಸಿ ಮತ್ತು ಮೆಗಾ ಕ್ವಿಜ್ ಆಡಿ")
        )

        val adapter = OnboardingAdapter(pages)
        binding.viewPager.adapter = adapter
        setupDots(pages.size, 0)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
                binding.btnNext.text = if (position == pages.size - 1) "Get Started!" else "Next"
            }
        })

        binding.btnNext.setOnClickListener {
            val cur = binding.viewPager.currentItem
            if (cur < pages.size - 1) binding.viewPager.currentItem = cur + 1 else launchMain()
        }
        binding.btnSkip.setOnClickListener { launchMain() }

        updateLangButtons()
        binding.btnEnglish.setOnClickListener {
            selectedKannada = false; LanguageManager.set(false); updateLangButtons()
        }
        binding.btnKannada.setOnClickListener {
            selectedKannada = true; LanguageManager.set(true); updateLangButtons()
        }
    }

    private fun setupDots(count: Int, selected: Int) {
        binding.dotsIndicator.removeAllViews(); dotViews.clear()
        val dp8 = (8 * resources.displayMetrics.density).toInt()
        val dp5 = (5 * resources.displayMetrics.density).toInt()
        for (i in 0 until count) {
            val dot = View(this)
            dot.background = android.graphics.drawable.GradientDrawable().apply {
                shape = android.graphics.drawable.GradientDrawable.OVAL
                setColor(if (i == selected) Color.parseColor("#E8553E") else Color.parseColor("#DDCCCC"))
            }
            val p = LinearLayout.LayoutParams(if (i == selected) dp8 else dp5, if (i == selected) dp8 else dp5)
            p.setMargins(4, 0, 4, 0); dot.layoutParams = p
            binding.dotsIndicator.addView(dot); dotViews.add(dot)
        }
    }

    private fun updateDots(selected: Int) {
        val dp8 = (8 * resources.displayMetrics.density).toInt()
        val dp5 = (5 * resources.displayMetrics.density).toInt()
        dotViews.forEachIndexed { i, dot ->
            val isSel = i == selected
            (dot.background as? android.graphics.drawable.GradientDrawable)?.setColor(
                if (isSel) Color.parseColor("#E8553E") else Color.parseColor("#DDCCCC"))
            val p = dot.layoutParams as LinearLayout.LayoutParams
            p.width = if (isSel) dp8 else dp5; p.height = p.width; dot.layoutParams = p
        }
    }

    private fun updateLangButtons() {
        binding.btnEnglish.alpha = if (selectedKannada) 0.5f else 1f
        binding.btnKannada.alpha = if (selectedKannada) 1f else 0.5f
    }

    private fun launchMain() {
        prefs.onboardingCompleted = true
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}

data class OnboardingPage(
    val emoji: String, val titleEn: String, val titleKn: String,
    val descEn: String, val descKn: String
)
