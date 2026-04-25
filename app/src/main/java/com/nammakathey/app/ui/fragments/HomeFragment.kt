package com.nammakathey.app.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nammakathey.app.data.model.District
import com.nammakathey.app.data.model.Hero
import com.nammakathey.app.databinding.FragmentHomeBinding
import com.nammakathey.app.ui.StoryActivity
import com.nammakathey.app.ui.adapters.HeroCardAdapter
import com.nammakathey.app.ui.viewmodel.MainViewModel
import com.nammakathey.app.util.LanguageManager
import com.nammakathey.app.util.toColorInt
import java.io.InputStream

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private var heroAdapter: HeroCardAdapter? = null

    private val langListener: () -> Unit = { applyLanguage() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
        setupHeroGrid()
        observeViewModel()
        LanguageManager.register(langListener)
        applyLanguage()

        // Scroll to very top when fragment loads
        binding.scrollView.post { binding.scrollView.scrollTo(0, 0) }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupMap() {
        binding.mapWebView.apply {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
            settings.setSupportZoom(true)
            settings.allowFileAccess = true
            settings.domStorageEnabled = true

            addJavascriptInterface(MapBridge(), "Android")

            webViewClient = object : WebViewClient() {
                override fun shouldInterceptRequest(
                    view: WebView, request: WebResourceRequest
                ): WebResourceResponse? {
                    val url = request.url.toString()
                    if (url.contains("karnataka_map.jpg")) {
                        return try {
                            val stream: InputStream =
                                requireContext().assets.open("map/karnataka_map.jpg")
                            WebResourceResponse("image/jpeg", "binary", stream)
                        } catch (e: Exception) { null }
                    }
                    return null
                }
            }
        }
        binding.mapWebView.loadUrl("file:///android_asset/map/karnataka_map.html")
    }

    private fun setupHeroGrid() {
        heroAdapter = HeroCardAdapter(
            isKannada = LanguageManager.isKannada,
            onHeroClick = { hero, district -> openStory(hero, district) }
        )
        binding.rvHeroes.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvHeroes.adapter = heroAdapter
    }

    private fun observeViewModel() {
        viewModel.selectedDistrict.observe(viewLifecycleOwner) { district ->
            district?.let { showDistrict(it) }
        }
        viewModel.districts.observe(viewLifecycleOwner) { districts ->
            // Do NOT auto-select first district — let user tap map
        }
    }

    private fun showDistrict(district: District) {
        val isKn = LanguageManager.isKannada

        // Update banner text and color
        binding.districtTitle.text = if (isKn) district.districtNameKn else district.districtName
        binding.districtSubtitle.text = "${district.heroes.size} " +
                if (isKn) "ವೀರರು" else "Heroes"
        binding.districtBanner.setCardBackgroundColor(district.colorHex.toColorInt())

        // Show banner and heroes if they were hidden
        binding.districtBanner.visibility = View.VISIBLE
        binding.heroesSection.visibility = View.VISIBLE
        binding.heroesSection.alpha = 0f
        binding.heroesSection.animate().alpha(1f).setDuration(280).start()

        // Load heroes
        heroAdapter?.submitHeroes(district.heroes, district)

        // Scroll so the district banner is just visible below the map
        binding.scrollView.post {
            binding.scrollView.smoothScrollTo(0, binding.districtBanner.top - 12)
        }
    }

    private fun applyLanguage() {
        val isKn = LanguageManager.isKannada

        // Update instruction text with translation
        binding.instructionText.text = if (isKn)
            "ನಿಮ್ಮ ಜಿಲ್ಲೆಯನ್ನು ಆಯ್ಕೆ ಮಾಡಿ 👇"
        else
            "Select a district of your choice 👇"

        // Update hero adapter language
        heroAdapter?.setLanguage(isKn)

        // Refresh district banner if already showing
        viewModel.selectedDistrict.value?.let { district ->
            binding.districtTitle.text = if (isKn) district.districtNameKn else district.districtName
            binding.districtSubtitle.text = "${district.heroes.size} " +
                    if (isKn) "ವೀರರು" else "Heroes"
        }
    }

    private fun openStory(hero: Hero, district: District) {
        viewModel.markStoryRead(hero.heroId, district.districtId)
        startActivity(Intent(requireContext(), StoryActivity::class.java).apply {
            putExtra(StoryActivity.EXTRA_HERO, hero)
            putExtra(StoryActivity.EXTRA_DISTRICT, district)
        })
    }

    inner class MapBridge {
        @JavascriptInterface
        fun onDistrictClick(districtId: String) {
            requireActivity().runOnUiThread {
                val district = viewModel.repo.getAllDistricts()
                    .find { it.districtId == districtId } ?: return@runOnUiThread
                viewModel.selectDistrict(district)
                binding.root.performHapticFeedback(
                    android.view.HapticFeedbackConstants.VIRTUAL_KEY
                )
            }
        }
    }

    override fun onDestroyView() {
        LanguageManager.unregister(langListener)
        binding.mapWebView.destroy()
        super.onDestroyView()
        _binding = null
    }
}
