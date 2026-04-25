package com.nammakathey.app.ui

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.nammakathey.app.R
import com.nammakathey.app.data.model.District
import com.nammakathey.app.data.model.Hero
import com.nammakathey.app.data.model.QuizQuestion
import com.nammakathey.app.databinding.ActivityQuizBinding
import com.nammakathey.app.ui.viewmodel.MainViewModel
import com.nammakathey.app.util.LanguageManager
import com.nammakathey.app.util.toColorInt

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    val viewModel: MainViewModel by viewModels()

    private lateinit var hero: Hero
    private lateinit var district: District
    private lateinit var questions: List<QuizQuestion>

    private var currentQ = 0
    private var score = 0
    private var answered = false

    companion object {
        const val EXTRA_HERO = "extra_hero"
        const val EXTRA_DISTRICT = "extra_district"
    }

    private val optionButtons by lazy {
        listOf(binding.btnOpt0, binding.btnOpt1, binding.btnOpt2, binding.btnOpt3)
    }

    private val langListener: () -> Unit = { applyLanguage() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hero = intent.getParcelableExtra(EXTRA_HERO)!!
        district = intent.getParcelableExtra(EXTRA_DISTRICT)!!
        questions = hero.quiz

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val color = district.colorHex.toColorInt()
        binding.toolbar.setBackgroundColor(color)
        window.statusBarColor = color

        LanguageManager.register(langListener)
        applyLanguage()
        showQuestion()
    }

    private fun applyLanguage() {
        val isKn = LanguageManager.isKannada
        binding.heroQuizTitle.text = if (isKn)
            "${hero.nameKn} - ಪ್ರಶ್ನೋತ್ತರಿ"
        else "${hero.nameEn} Quiz"
        // Refresh current question display if already shown
        if (::questions.isInitialized && !answered) refreshQuestionText()
    }

    private fun refreshQuestionText() {
        if (currentQ >= questions.size) return
        val isKn = LanguageManager.isKannada
        val q = questions[currentQ]
        binding.questionText.text = q.displayQuestion(isKn)
        val opts = q.displayOptions(isKn)
        optionButtons.forEachIndexed { i, btn -> btn.text = opts.getOrElse(i) { "" } }
    }

    private fun showQuestion() {
        answered = false
        val isKn = LanguageManager.isKannada
        val q = questions[currentQ]

        binding.questionCounter.text = "${currentQ + 1} / ${questions.size}"
        binding.progressBar.progress = ((currentQ.toFloat() / questions.size) * 100).toInt()
        binding.questionText.text = q.displayQuestion(isKn)
        binding.feedbackText.visibility = View.INVISIBLE

        val opts = q.displayOptions(isKn)
        optionButtons.forEachIndexed { i, btn ->
            btn.text = opts.getOrElse(i) { "" }
            btn.isEnabled = true
            btn.setBackgroundResource(R.drawable.bg_option_default)
            btn.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
            btn.setOnClickListener { onOptionSelected(i) }
        }
        binding.btnNextQuestion.visibility = View.GONE
        binding.quizCard.alpha = 0f
        binding.quizCard.animate().alpha(1f).setDuration(220).start()
    }

    private fun onOptionSelected(selectedIndex: Int) {
        if (answered) return
        answered = true
        val isKn = LanguageManager.isKannada
        val q = questions[currentQ]
        val correct = selectedIndex == q.correctIndex
        if (correct) score++

        optionButtons.forEachIndexed { i, btn ->
            btn.isEnabled = false
            when {
                i == q.correctIndex -> { btn.setBackgroundResource(R.drawable.bg_option_correct); btn.setTextColor(Color.WHITE) }
                i == selectedIndex && !correct -> { btn.setBackgroundResource(R.drawable.bg_option_wrong); btn.setTextColor(Color.WHITE) }
            }
        }

        binding.feedbackText.text = if (correct)
            if (isKn) "✅ ಅದ್ಭುತ! ಸರಿಯಾದ ಉತ್ತರ!" else "✅ Correct! Well done!"
        else {
            val ans = q.displayOptions(isKn)[q.correctIndex]
            if (isKn) "❌ ತಪ್ಪು. ಸರಿ: $ans" else "❌ Wrong. Correct: $ans"
        }
        binding.feedbackText.setTextColor(
            ContextCompat.getColor(this, if (correct) R.color.success_green else R.color.error_red))
        binding.feedbackText.visibility = View.VISIBLE
        binding.btnNextQuestion.visibility = View.VISIBLE

        val isLast = currentQ == questions.size - 1
        binding.btnNextQuestion.text = if (isLast)
            if (isKn) "ಫಲಿತಾಂಶ ನೋಡಿ" else "See Results"
        else if (isKn) "ಮುಂದಿನ ▶" else "Next ▶"
        binding.btnNextQuestion.setOnClickListener {
            if (isLast) showResults() else { currentQ++; showQuestion() }
        }
    }

    private fun showResults() {
        val isKn = LanguageManager.isKannada
        val passed = score == questions.size
        binding.quizLayout.visibility = View.GONE
        binding.resultsLayout.visibility = View.VISIBLE

        binding.resultEmoji.text = if (passed) "🏅" else "📚"
        binding.resultScore.text = "$score / ${questions.size}"
        binding.resultTitle.text = if (passed)
            if (isKn) "ಅಭಿನಂದನೆಗಳು!" else "Congratulations!"
        else if (isKn) "ಮತ್ತೊಮ್ಮೆ ಪ್ರಯತ್ನಿಸಿ!" else "Keep Learning!"

        binding.resultSubtitle.text = if (passed)
            if (isKn) "ನೀವು ${hero.badgeNameKn} ಬ್ಯಾಡ್ಜ್ ಗಳಿಸಿದ್ದೀರಿ!" else "You earned the ${hero.badgeNameEn} badge!"
        else if (isKn) "ಕಥೆ ಮತ್ತೆ ಓದಿ ಪ್ರಯತ್ನಿಸಿ." else "Re-read the story and try again."

        if (passed) {
            binding.badgeCard.visibility = View.VISIBLE
            binding.badgeEmojiResult.text = hero.imageEmoji
            binding.badgeNameResult.text = hero.displayBadgeName(isKn)
            viewModel.earnBadge(hero, district)
            viewModel.markQuizCompleted(hero.heroId, district.districtId)
        } else { binding.badgeCard.visibility = View.GONE }

        binding.btnDone.text = if (isKn) "ಮುಖಪುಟಕ್ಕೆ" else "Back to Home"
        binding.btnDone.setOnClickListener { finish() }
        binding.btnRetry.visibility = if (!passed) View.VISIBLE else View.GONE
        binding.btnRetry.text = if (isKn) "ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ" else "Try Again"
        binding.btnRetry.setOnClickListener {
            currentQ = 0; score = 0
            binding.quizLayout.visibility = View.VISIBLE
            binding.resultsLayout.visibility = View.GONE
            showQuestion()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { onBackPressedDispatcher.onBackPressed(); return true }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        LanguageManager.unregister(langListener)
        super.onDestroy()
    }
}
