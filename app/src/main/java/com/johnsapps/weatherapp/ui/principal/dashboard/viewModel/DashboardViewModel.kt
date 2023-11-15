package com.johnsapps.weatherapp.ui.principal.dashboard.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnsapps.weatherapp.domain.models.toListOfItemUI
import com.johnsapps.weatherapp.domain.models.toWeatherUI
import com.johnsapps.weatherapp.domain.useCase.GetNameUseCase
import com.johnsapps.weatherapp.domain.useCase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getNameUseCase: GetNameUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<DashboardUIState>(DashboardUIState.Loading)
    val uiState: StateFlow<DashboardUIState> = _uiState

    fun getNameUser() {
        _uiState.value = DashboardUIState.Loading
        viewModelScope.launch {
            getNameUseCase.invoke()
                .catch {
                    _uiState.value = DashboardUIState.Error(it.message.orEmpty())
                    Log.d("ErrorLogin", it.message.orEmpty())
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    it?.let { _uiState.value = DashboardUIState.UserNameSuccess(it.name) }
                }
        }
    }

    fun getWeather(q: String) {
        _uiState.value = DashboardUIState.Loading
        viewModelScope.launch {
            getWeatherUseCase.getWeather(q)
                .catch {
                    _uiState.value = DashboardUIState.Error(it.message.orEmpty())
                    Log.d("ErrorLogin", it.message.orEmpty())
                }
                .flowOn(Dispatchers.IO)
                .collect{
                    _uiState.value = DashboardUIState.ServiceSuccess(it.toListOfItemUI(),it.toWeatherUI())
                    Log.d("ErrorLogin", it.toString())
                }
        }
    }
}