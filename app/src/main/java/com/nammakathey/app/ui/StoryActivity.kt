package com.nammakathey.app.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.nammakathey.app.R
import com.nammakathey.app.data.model.District
import com.nammakathey.app.data.model.Hero
import com.nammakathey.app.databinding.ActivityStoryBinding
import com.nammakathey.app.ui.adapters.StoryPagerAdapter
import com.nammakathey.app.util.LanguageManager
import com.nammakathey.app.util.TtsManager
import com.nammakathey.app.util.toColorInt

class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding
    private lateinit var ttsManager: TtsManager
    private lateinit var hero: Hero
    private lateinit var district: District
    private var currentPage = 0
    private val dotViews = mutableListOf<View>()
    private lateinit var storyAdapter: StoryPagerAdapter

    companion object {
        const val EXTRA_HERO = "extra_hero"
        const val EXTRA_DISTRICT = "extra_district"
    }

    private val langListener: () -> Unit = { applyLanguage() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hero = intent.getParcelableExtra(EXTRA_HERO)!!
        district = intent.getParcelableExtra(EXTRA_DISTRICT)!!

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val color = district.colorHex.toColorInt()
        binding.toolbar.setBackgroundColor(color)
        binding.heroInfoBar.setBackgroundColor(color)
        window.statusBarColor = color

        ttsManager = TtsManager(this)
        ttsManager.onSpeakingStateChanged = { speaking ->
            runOnUiThread {
                binding.btnTts.setImageResource(
                    if (speaking) R.drawable.ic_stop else R.drawable.ic_speaker
                )
            }
        }

        val isKn = LanguageManager.isKannada
        storyAdapter = StoryPagerAdapter(hero.storyPages, isKn, district.colorHex)
        binding.viewPager.adapter = storyAdapter
        setupDots(hero.storyPages.size, 0)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPage = position
                ttsManager.stop()
                updateDots(position)
                updateNavButtons()
                updatePageCounter()
            }
        })

        binding.btnPrev.setOnClickListener {
            if (binding.viewPager.currentItem > 0)
                binding.viewPager.currentItem = binding.viewPager.currentItem - 1
        }

        binding.btnNext.setOnClickListener {
            val cur = binding.viewPager.currentItem
            if (cur < hero.storyPages.size - 1) {
                binding.viewPager.currentItem = cur + 1
            } else {
                ttsManager.stop()
                startActivity(Intent(this, QuizActivity::class.java).apply {
                    putExtra(QuizActivity.EXTRA_HERO, hero)
                    putExtra(QuizActivity.EXTRA_DISTRICT, district)
                })
            }
        }

        binding.btnTts.setOnClickListener {
            val page = hero.storyPages[currentPage]
            val isKannada = LanguageManager.isKannada
            val text = "${page.displayTitle(isKannada)}. ${page.displayContent(isKannada)}"
            ttsManager.toggle(text, isKannada)
        }

        LanguageManager.register(langListener)
        applyLanguage()
    }

    private fun applyLanguage() {
        val isKn = LanguageManager.isKannada
        binding.heroName.text = hero.displayName(isKn)
        binding.heroTagline.text = hero.displayTagline(isKn)
        storyAdapter.setLanguage(isKn)
        updateNavButtons()
        updatePageCounter()
    }

    private fun updateNavButtons() {
        val isKn = LanguageManager.isKannada
        val last = currentPage == hero.storyPages.size - 1
        binding.btnNext.text = if (last)
            if (isKn) "ಪ್ರಶ್ನೋತ್ತರಿ ▶" else "Take Quiz ▶"
        else
            if (isKn) "ಮುಂದೆ ▶" else "Next ▶"
        binding.btnPrev.text = if (isKn) "◀ ಹಿಂದೆ" else "◀ Prev"
        binding.btnPrev.isEnabled = currentPage > 0
        binding.btnPrev.alpha = if (currentPage > 0) 1f else 0.35f
    }

    private fun updatePageCounter() {
        binding.pageCounter.text = "${currentPage + 1} / ${hero.storyPages.size}"
    }

    private fun setupDots(count: Int, selected: Int) {
        binding.dotsIndicator.removeAllViews()
        dotViews.clear()
        val dp8 = (8 * resources.displayMetrics.density).toInt()
        val dp5 = (5 * resources.displayMetrics.density).toInt()
        val primaryColor = district.colorHex.toColorInt()
        for (i in 0 until count) {
            val dot = View(this)
            dot.background = android.graphics.drawable.GradientDrawable().apply {
                shape = android.graphics.drawable.GradientDrawable.OVAL
                setColor(if (i == selected) primaryColor else Color.parseColor("#DDCCCC"))
            }
            val params = LinearLayout.LayoutParams(if (i == selected) dp8 else dp5, if (i == selected) dp8 else dp5)
            params.setMargins(4, 0, 4, 0)
            dot.layoutParams = params
            binding.dotsIndicator.addView(dot)
            dotViews.add(dot)
        }
    }

    private fun updateDots(selected: Int) {
        val dp8 = (8 * resources.displayMetrics.density).toInt()
        val dp5 = (5 * resources.displayMetrics.density).toInt()
        val primaryColor = district.colorHex.toColorInt()
        dotViews.forEachIndexed { i, dot ->
            val isSel = i == selected
            (dot.background as? android.graphics.drawable.GradientDrawable)?.setColor(
                if (isSel) primaryColor else Color.parseColor("#DDCCCC"))
            val p = dot.layoutParams as LinearLayout.LayoutParams
            p.width = if (isSel) dp8 else dp5; p.height = p.width
            dot.layoutParams = p
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { onBackPressedDispatcher.onBackPressed(); return true }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        LanguageManager.unregister(langListener)
        ttsManager.shutdown()
        super.onDestroy()
    }
}
