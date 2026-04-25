package com.nammakathey.app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nammakathey.app.databinding.FragmentSearchBinding
import com.nammakathey.app.ui.StoryActivity
import com.nammakathey.app.ui.adapters.SearchResultAdapter
import com.nammakathey.app.ui.viewmodel.MainViewModel
import com.nammakathey.app.util.LanguageManager

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private var adapter: SearchResultAdapter? = null

    private val langListener: () -> Unit = { applyLanguage() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchResultAdapter(LanguageManager.isKannada) { hero, district ->
            startActivity(Intent(requireContext(), StoryActivity::class.java).apply {
                putExtra(StoryActivity.EXTRA_HERO, hero)
                putExtra(StoryActivity.EXTRA_DISTRICT, district)
            })
        }
        binding.rvSearchResults.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchResults.adapter = adapter

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {
                viewModel.search(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.searchResults.observe(viewLifecycleOwner) { results ->
            adapter?.submitList(results)
            val query = binding.searchInput.text.toString()
            val isKn = LanguageManager.isKannada
            binding.emptyState.visibility = if (query.isNotBlank() && results.isEmpty()) View.VISIBLE else View.GONE
            binding.emptyStateText.text = if (isKn) "'$query' ಗೆ ಯಾವುದೂ ಸಿಗಲಿಲ್ಲ"
            else "No results for '$query'"
        }

        LanguageManager.register(langListener)
        applyLanguage()
    }

    private fun applyLanguage() {
        val isKn = LanguageManager.isKannada
        binding.searchInput.hint = if (isKn) "ವೀರನ ಹೆಸರು ಹುಡುಕಿ..." else "Search for a hero..."
        adapter?.setLanguage(isKn)
        // Re-run search with current query to refresh results in new language
        val q = binding.searchInput.text?.toString() ?: ""
        if (q.isNotBlank()) viewModel.search(q)
    }

    override fun onDestroyView() {
        LanguageManager.unregister(langListener)
        super.onDestroyView()
        _binding = null
    }
}
