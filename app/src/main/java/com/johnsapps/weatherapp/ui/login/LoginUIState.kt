package com.johnsapps.weatherapp.ui.login

sealed class LoginUIState {
    data object Begin : LoginUIState()
    data object Loading : LoginUIState()
    data class Success(val isSessionActive: Boolean) : LoginUIState()
    data class Error(val msg: String) : LoginUIState()
    data class ErrorField(val resource: Int) : LoginUIState()
}