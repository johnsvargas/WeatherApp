package com.johnsapps.weatherapp.ui.principal.profile.viewModel


sealed class ProfileUIState {
    data object Initial : ProfileUIState()
    data object Loading : ProfileUIState()
    data class Success(val userUI: UserUI, val isUpdateSuccessfully: Boolean = false) : ProfileUIState()
    data object Logout: ProfileUIState()
    data class Error(val msg: String) : ProfileUIState()
}