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
import com.nammakathey.app.databinding.ActivityMegaQuizBinding
import com.nammakathey.app.ui.viewmodel.MainViewModel
import com.nammakathey.app.util.LanguageManager

data class MegaQuizItem(val question: QuizQuestion, val hero: Hero, val district: District)

class MegaQuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMegaQuizBinding
    private val viewModel: MainViewModel by viewModels()
    private var questions: List<MegaQuizItem> = emptyList()
    private var currentQ = 0
    private var score = 0
    private var answered = false
    private val TOTAL = 10

    private val optionButtons by lazy {
        listOf(binding.btnOpt0, binding.btnOpt1, binding.btnOpt2, binding.btnOpt3)
    }

    private val langListener: () -> Unit = {
        if (!answered) refreshQuestion()
        applyStaticLabels()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMegaQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.brand_primary))
        window.statusBarColor = ContextCompat.getColor(this, R.color.brand_primary)

        LanguageManager.register(langListener)
        buildQuestions()
        applyStaticLabels()
        showQuestion()
    }

    private fun applyStaticLabels() {
        val isKn = LanguageManager.isKannada
        binding.megaQuizTitle.text = if (isKn) "ಕರ್ನಾಟಕ ಮೆಗಾ ಪ್ರಶ್ನೋತ್ತರಿ 🏆" else "Karnataka Mega Quiz 🏆"
    }

    private fun buildQuestions() {
        val all = viewModel.repo.getAllDistricts().flatMap { district ->
            district.heroes.flatMap { hero ->
                hero.quiz.map { q -> MegaQuizItem(q, hero, district) }
            }
        }.shuffled()
        questions = all.take(TOTAL)
    }

    private fun refreshQuestion() {
        if (currentQ >= questions.size) return
        val isKn = LanguageManager.isKannada
        val item = questions[currentQ]
        binding.questionText.text = item.question.displayQuestion(isKn)
        val opts = item.question.displayOptions(isKn)
        optionButtons.forEachIndexed { i, btn -> btn.text = opts.getOrElse(i) { "" } }
        binding.heroContextChip.text = "${item.hero.imageEmoji}  ${item.hero.displayName(isKn)} · ${if (isKn) item.district.districtNameKn else item.district.districtName}"
    }

    private fun showQuestion() {
        if (currentQ >= questions.size) { showResults(); return }
        answered = false
        val isKn = LanguageManager.isKannada
        val item = questions[currentQ]
        val q = item.question

        binding.questionCounter.text = "${currentQ + 1} / ${questions.size}"
        binding.progressBar.progress = ((currentQ.toFloat() / questions.size) * 100).toInt()
        binding.heroContextChip.text = "${item.hero.imageEmoji}  ${item.hero.displayName(isKn)} · ${if (isKn) item.district.districtNameKn else item.district.districtName}"
        binding.questionText.text = q.displayQuestion(isKn)
        binding.feedbackText.visibility = View.INVISIBLE
        binding.btnNextQuestion.visibility = View.GONE

        val opts = q.displayOptions(isKn)
        optionButtons.forEachIndexed { i, btn ->
            btn.text = opts.getOrElse(i) { "" }
            btn.isEnabled = true
            btn.setBackgroundResource(R.drawable.bg_option_default)
            btn.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
            btn.setOnClickListener { onOptionSelected(i) }
        }
        binding.quizCard.alpha = 0f
        binding.quizCard.animate().alpha(1f).setDuration(220).start()
    }

    private fun onOptionSelected(selectedIndex: Int) {
        if (answered) return
        answered = true
        val isKn = LanguageManager.isKannada
        val q = questions[currentQ].question
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
            if (isKn) "✅ ಸರಿ!" else "✅ Correct!"
        else { val ans = q.displayOptions(isKn)[q.correctIndex]; if (isKn) "❌ ತಪ್ಪು. ಸರಿ: $ans" else "❌ Wrong. Correct: $ans" }
        binding.feedbackText.setTextColor(ContextCompat.getColor(this, if (correct) R.color.success_green else R.color.error_red))
        binding.feedbackText.visibility = View.VISIBLE
        binding.btnNextQuestion.visibility = View.VISIBLE

        val isLast = currentQ == questions.size - 1
        binding.btnNextQuestion.text = if (isLast) if (isKn) "ಫಲಿತಾಂಶ 🏆" else "Results 🏆" else if (isKn) "ಮುಂದಿನ ▶" else "Next ▶"
        binding.btnNextQuestion.setOnClickListener { if (isLast) showResults() else { currentQ++; showQuestion() } }
    }

    private fun showResults() {
        val isKn = LanguageManager.isKannada
        val pct = (score * 100) / questions.size
        binding.quizLayout.visibility = View.GONE
        binding.resultsLayout.visibility = View.VISIBLE

        binding.resultEmoji.text = when { pct >= 90 -> "🏆"; pct >= 70 -> "🥇"; pct >= 50 -> "🥈"; else -> "📚" }
        binding.resultScore.text = "$score / ${questions.size}"
        binding.resultPercent.text = "$pct%"
        binding.resultTitle.text = when {
            pct >= 90 -> if (isKn) "ಅಮೋಘ! ಕರ್ನಾಟಕ ತಜ್ಞರು! 🌟" else "Outstanding! Karnataka Expert! 🌟"
            pct >= 70 -> if (isKn) "ಅದ್ಭುತ ಪ್ರದರ್ಶನ! 🎉" else "Excellent Performance! 🎉"
            pct >= 50 -> if (isKn) "ಉತ್ತಮ ಪ್ರಯತ್ನ! 👍" else "Good Effort! 👍"
            else -> if (isKn) "ಮತ್ತೊಮ್ಮೆ ಪ್ರಯತ್ನಿಸಿ! 💪" else "Keep Learning! 💪"
        }
        binding.btnDone.text = if (isKn) "ಮುಖಪುಟಕ್ಕೆ" else "Back to Home"
        binding.btnDone.setOnClickListener { finish() }
        binding.btnRetryMega.text = if (isKn) "ಮತ್ತೆ ಆಡಿ 🔄" else "Play Again 🔄"
        binding.btnRetryMega.setOnClickListener {
            currentQ = 0; score = 0; buildQuestions()
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
