package com.nammakathey.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nammakathey.app.databinding.FragmentBadgesBinding
import com.nammakathey.app.ui.adapters.BadgeAdapter
import com.nammakathey.app.ui.viewmodel.MainViewModel
import com.nammakathey.app.util.LanguageManager

class BadgesFragment : Fragment() {

    private var _binding: FragmentBadgesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private var adapter: BadgeAdapter? = null

    private val langListener: () -> Unit = { applyLanguage() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBadgesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BadgeAdapter(LanguageManager.isKannada)
        binding.rvBadges.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvBadges.adapter = adapter

        viewModel.allBadges.observe(viewLifecycleOwner) { badges ->
            adapter?.submitList(badges)
            applyLanguage()
        }

        LanguageManager.register(langListener)
        applyLanguage()
    }

    private fun applyLanguage() {
        val isKn = LanguageManager.isKannada
        val badges = viewModel.allBadges.value ?: emptyList()
        adapter?.setLanguage(isKn)

        if (badges.isEmpty()) {
            binding.emptyState.visibility = View.VISIBLE
            binding.rvBadges.visibility = View.GONE
            binding.emptyStateText.text = if (isKn)
                "ಇನ್ನೂ ಯಾವುದೇ ಬ್ಯಾಡ್ಜ್ ಇಲ್ಲ!\nಕಥೆ ಓದಿ ಪ್ರಶ್ನೋತ್ತರಿ ಮಾಡಿ."
            else "No badges yet!\nRead stories and complete quizzes."
        } else {
            binding.emptyState.visibility = View.GONE
            binding.rvBadges.visibility = View.VISIBLE
        }

        binding.badgeCountText.text = if (isKn)
            "${badges.size} ಬ್ಯಾಡ್ಜ್‌ಗಳು ಗಳಿಸಲಾಗಿದೆ"
        else "${badges.size} Badge${if (badges.size != 1) "s" else ""} Earned"
    }

    override fun onDestroyView() {
        LanguageManager.unregister(langListener)
        super.onDestroyView()
        _binding = null
    }
}
