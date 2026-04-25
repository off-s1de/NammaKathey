package com.nammakathey.app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nammakathey.app.databinding.FragmentBookmarksBinding
import com.nammakathey.app.ui.StoryActivity
import com.nammakathey.app.ui.adapters.BookmarkAdapter
import com.nammakathey.app.ui.viewmodel.MainViewModel
import com.nammakathey.app.util.LanguageManager

class BookmarksFragment : Fragment() {

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private var adapter: BookmarkAdapter? = null

    private val langListener: () -> Unit = { applyLanguage() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BookmarkAdapter(LanguageManager.isKannada) { bookmark ->
            val hero = viewModel.repo.getHero(bookmark.heroId) ?: return@BookmarkAdapter
            val district = viewModel.repo.getDistrict(bookmark.districtId) ?: return@BookmarkAdapter
            startActivity(Intent(requireContext(), StoryActivity::class.java).apply {
                putExtra(StoryActivity.EXTRA_HERO, hero)
                putExtra(StoryActivity.EXTRA_DISTRICT, district)
            })
        }
        binding.rvBookmarks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBookmarks.adapter = adapter

        viewModel.allBookmarks.observe(viewLifecycleOwner) { bookmarks ->
            adapter?.submitList(bookmarks)
            applyLanguage()
        }

        LanguageManager.register(langListener)
        applyLanguage()
    }

    private fun applyLanguage() {
        val isKn = LanguageManager.isKannada
        adapter?.setLanguage(isKn)
        val bookmarks = viewModel.allBookmarks.value ?: emptyList()
        if (bookmarks.isEmpty()) {
            binding.emptyState.visibility = View.VISIBLE
            binding.rvBookmarks.visibility = View.GONE
            binding.emptyStateText.text = if (isKn)
                "ಯಾವುದೇ ಕಥೆ ಉಳಿಸಲಾಗಿಲ್ಲ.\nಕಥೆ ಓದುವಾಗ ★ ಒತ್ತಿ ಉಳಿಸಿ."
            else "No bookmarks yet.\nTap ★ while reading to save stories."
        } else {
            binding.emptyState.visibility = View.GONE
            binding.rvBookmarks.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        LanguageManager.unregister(langListener)
        super.onDestroyView()
        _binding = null
    }
}
