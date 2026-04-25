package com.nammakathey.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nammakathey.app.R
import com.nammakathey.app.databinding.ActivityMainBinding
import com.nammakathey.app.ui.viewmodel.MainViewModel
import com.nammakathey.app.util.LanguageManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    private val langListener: () -> Unit = {
        updateAllLabels()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

        viewModel.loadDistricts()

        // Language toggle — instant across entire app
        binding.btnLangToggle.setOnClickListener {
            LanguageManager.toggle()
            // Also update ViewModel for fragments that observe LiveData
            viewModel.setKannada(LanguageManager.isKannada)
        }

        // Mega Quiz
        binding.btnMegaQuiz.setOnClickListener {
            startActivity(Intent(this, MegaQuizActivity::class.java))
        }

        // Update nav titles when destination changes
        navController.addOnDestinationChangedListener { _, _, _ -> updateAllLabels() }

        // Register global language listener
        LanguageManager.register(langListener)
        updateAllLabels()
    }

    private fun updateAllLabels() {
        val isKn = LanguageManager.isKannada

        // Language button: show current-lang indicator
        // "🌐 Aa" = currently English,  "🌐 ಅ" = currently Kannada
        binding.langLabel.text = if (isKn) "Aa" else "ಅ"

        // Toolbar title based on current screen
        binding.toolbarTitle.text = when (navController.currentDestination?.id) {
            R.id.homeFragment         -> if (isKn) "ನಮ್ಮ ಕಥೆ" else "Namma Kathey"
            R.id.badgesFragment       -> if (isKn) "ನನ್ನ ಬ್ಯಾಡ್ಜ್‌ಗಳು" else "My Badges"
            R.id.searchFragment       -> if (isKn) "ಹುಡುಕಿ" else "Search"
            R.id.bookmarksFragment    -> if (isKn) "ಉಳಿಸಿದ ಕಥೆಗಳು" else "Bookmarks"
            R.id.statuefinderFragment -> if (isKn) "ಸ್ಮಾರಕ ಹುಡುಕಿ" else "Statue Finder"
            else                      -> if (isKn) "ನಮ್ಮ ಕಥೆ" else "Namma Kathey"
        }
    }

    override fun onDestroy() {
        LanguageManager.unregister(langListener)
        super.onDestroy()
    }
}
