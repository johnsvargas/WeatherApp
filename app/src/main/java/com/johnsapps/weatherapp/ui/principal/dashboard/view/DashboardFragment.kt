package com.johnsapps.weatherapp.ui.principal.dashboard.view

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.johnsapps.weatherapp.R
import com.johnsapps.weatherapp.databinding.FragmentDashboardBinding
import com.johnsapps.weatherapp.ui.principal.dashboard.viewModel.DashboardUIState
import com.johnsapps.weatherapp.ui.principal.dashboard.viewModel.DashboardViewModel
import com.johnsapps.weatherapp.ui.principal.dashboard.viewModel.uiModels.ItemUI
import com.johnsapps.weatherapp.ui.principal.dashboard.viewModel.uiModels.WeatherLocaleUI
import com.johnsapps.weatherapp.ui.uitils.makeToastFast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var adapter: ItemUIAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getLastLocation()
        } else {
            this.makeToastFast("Acepta los permisos para poder disfrutar de una experiencia mágica")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initState()
        initLocation()
        configSwipeRefresh()

        if (checkLocationPermission()) {
            getLastLocation()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun configSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            getLastLocation()
        }
    }
    private fun initRecyclerView(listItemUI: List<ItemUI>) {
        adapter = ItemUIAdapter(listItemUI)
        binding.rvListCards.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvListCards.adapter = adapter
    }

    private fun initState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is DashboardUIState.Error -> {
                            binding.progressBar.isVisible = false
                            binding.swipeRefresh.isRefreshing = false
                                Toast.makeText(
                                requireContext(),
                                "Ha ocurrido un error: ${uiState.msg}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is DashboardUIState.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is DashboardUIState.UserNameSuccess -> {
                            binding.progressBar.isVisible = false
                            binding.tvUserTitle.text = String.format(
                                resources.getString(R.string.label_greeting),
                                uiState.name
                            )
                        }

                        is DashboardUIState.ServiceSuccess -> {
                            binding.swipeRefresh.isRefreshing = false
                            binding.progressBar.isVisible = false
                            setWeather(uiState.weather)
                            initRecyclerView(uiState.listItemUI)
                        }
                    }
                }
            }
        }
        viewModel.getNameUser()
    }

    private fun setWeather(weather: WeatherLocaleUI) {
        binding.tvValueSunRise.text = weather.sunRise
        binding.tvValueSunSet.text = weather.sunSet
        binding.tvTitleCity.text = weather.name
        binding.tvTitleCountry.text = weather.country
    }

    private fun initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private fun getLastLocation() {
        if (checkLocationPermission()) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        this.makeToastFast("latitud $latitude y longitud $longitude")
                        viewModel.getWeather("$latitude,$longitude")
                    }
                }
        }
    }

    private fun checkLocationPermission(): Boolean {
        return PermissionChecker.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PermissionChecker.PERMISSION_GRANTED
    }

    override fun onResume() {
        super.onResume()
        activity?.title = requireContext().getString(R.string.label_dashboard_screen_name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}