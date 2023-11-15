package com.johnsapps.weatherapp.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnsapps.weatherapp.R
import com.johnsapps.weatherapp.domain.useCase.IsSessionActiveUseCase
import com.johnsapps.weatherapp.ui.uitils.isEmptyOrBlank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: IsSessionActiveUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Begin)
    val uiState: StateFlow<LoginUIState> = _uiState

    fun isActiveSession() {
        viewModelScope.launch {
            useCase.getUser()
                .catch {
                    _uiState.value = LoginUIState.Error(it.message.orEmpty())
                    Log.d("ErrorLogin",it.message.orEmpty())
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _uiState.value = LoginUIState.Success(it != null)
                }
        }
    }

    fun checkField(value: String): Boolean {
        if(value.isEmptyOrBlank()){
            _uiState.value = LoginUIState.ErrorField(R.string.label_error_field_empty)
            return false
        }
        return true
    }

    fun login(user: String) {
        viewModelScope.launch {
            useCase.insertUser(user)
        }
    }
}