package com.nammakathey.app.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nammakathey.app.databinding.FragmentStatuefinderBinding
import com.nammakathey.app.ui.adapters.StatueAdapter
import com.nammakathey.app.ui.viewmodel.MainViewModel
import com.nammakathey.app.util.LanguageManager
import kotlin.math.*

class StatueFinderFragment : Fragment() {

    private var _binding: FragmentStatuefinderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val langListener: () -> Unit = { applyLanguage() }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { perms ->
        if (perms[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true) getLocation()
        else showAllStatues(null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStatuefinderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding.rvStatues.layoutManager = LinearLayoutManager(requireContext())
        checkLocationPermission()
        LanguageManager.register(langListener)
        applyLanguage()
    }

    private fun applyLanguage() {
        val isKn = LanguageManager.isKannada
        binding.headerText.text = if (isKn) "ಹತ್ತಿರದ ಸ್ಮಾರಕಗಳು" else "Nearby Memorials"
        binding.subHeaderText.text = if (isKn)
            "ನಿಮ್ಮ ಸ್ಥಳದ ಆಧಾರದ ಮೇಲೆ ಹತ್ತಿರದ ಸ್ಮಾರಕ"
        else "Finding memorials near your location"
        // re-render list in new language
        (binding.rvStatues.adapter as? StatueAdapter)?.setLanguage(isKn)
    }

    private fun checkLocationPermission() {
        val fine = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        val coarse = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
        if (fine == PackageManager.PERMISSION_GRANTED || coarse == PackageManager.PERMISSION_GRANTED) getLocation()
        else locationPermissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun getLocation() {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { loc -> showAllStatues(loc) }
                .addOnFailureListener { showAllStatues(null) }
        } catch (e: SecurityException) { showAllStatues(null) }
    }

    private fun showAllStatues(userLocation: Location?) {
        val isKn = LanguageManager.isKannada
        val allStatues = viewModel.repo.getAllStatueLocations()
        val withDistance = allStatues.map { (statue, hero) ->
            val distKm = userLocation?.let {
                haversine(it.latitude, it.longitude, statue.latitude, statue.longitude)
            }
            Triple(statue, hero, distKm)
        }.sortedBy { it.third ?: Double.MAX_VALUE }

        val adapter = StatueAdapter(isKn, withDistance)
        binding.rvStatues.adapter = adapter

        binding.locationStatus.text = if (userLocation != null) {
            val nearest = withDistance.firstOrNull()
            if (nearest?.third != null)
                "📍 " + if (isKn) "ಹತ್ತಿರದ: ${nearest.first.displayName(isKn)} (${String.format("%.1f", nearest.third)} km)"
                else "Nearest: ${nearest.first.displayName(isKn)} (${String.format("%.1f", nearest.third)} km away)"
            else ""
        } else if (isKn) "⚠️ ಸ್ಥಳ ಅನುಮತಿ ಇಲ್ಲ. ಎಲ್ಲ ಸ್ಮಾರಕಗಳನ್ನು ತೋರಿಸಲಾಗುತ್ತಿದೆ."
        else "⚠️ Location unavailable. Showing all memorials."
    }

    private fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2)
        return r * 2 * atan2(sqrt(a), sqrt(1 - a))
    }

    override fun onDestroyView() {
        LanguageManager.unregister(langListener)
        super.onDestroyView()
        _binding = null
    }
}
