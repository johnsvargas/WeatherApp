package com.johnsapps.weatherapp.ui.principal.dashboard.viewModel

import com.johnsapps.weatherapp.ui.principal.dashboard.viewModel.uiModels.ItemUI
import com.johnsapps.weatherapp.ui.principal.dashboard.viewModel.uiModels.WeatherLocaleUI

sealed class DashboardUIState {
    data object Loading : DashboardUIState()
    data class UserNameSuccess(val name: String):DashboardUIState()
    data class ServiceSuccess(val listItemUI: List<ItemUI>, val weather: WeatherLocaleUI): DashboardUIState()
    data class Error(val msg: String) : DashboardUIState()
}