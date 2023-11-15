package com.johnsapps.weatherapp.ui.principal.profile.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnsapps.weatherapp.R
import com.johnsapps.weatherapp.domain.models.toUserUI
import com.johnsapps.weatherapp.domain.useCase.GetUserDataUseCase
import com.johnsapps.weatherapp.ui.uitils.isEmailFormatIsNotCorrected
import com.johnsapps.weatherapp.ui.uitils.isEmptyOrBlank
import com.johnsapps.weatherapp.ui.uitils.isPhoneNumberIsNotCorrected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUIState>(ProfileUIState.Loading)
    val uiState: StateFlow<ProfileUIState> = _uiState

    private var _phoneNumberError: MutableLiveData<Int> = MutableLiveData()
    val phoneNumberError: LiveData<Int> = _phoneNumberError

    private var _emailError: MutableLiveData<Int> = MutableLiveData()
    val emailError: LiveData<Int> = _emailError



    private var userUI: UserUI? = null
    private var idUser = 0
    private var isUpdateSuccessfully = false

    fun getUserData() {
        _uiState.value = ProfileUIState.Loading
        viewModelScope.launch {
            getUserDataUseCase.user
                .catch {
                    _uiState.value = ProfileUIState.Error(it.message.orEmpty())
                    Log.d("ErrorLogin", it.message.orEmpty())
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    it?.let { user ->
                        userUI = user.toUserUI()
                        user.id?.let { id -> idUser = id }
                        _uiState.value = ProfileUIState.Success(user.toUserUI(), isUpdateSuccessfully)
                        isUpdateSuccessfully = false
                    }
                    if (it == null) {
                        _uiState.value = ProfileUIState.Logout
                    }
                }
        }
    }

    fun logOut() {
        _uiState.value = ProfileUIState.Loading
        viewModelScope.launch {
            userUI?.toUser(idUser)?.let { user ->
                getUserDataUseCase.deleteUserData(user)
            }
        }
    }

    fun checkFields(email: String, phoneNumber: String): Boolean {
        if (email.isEmptyOrBlank()) {
            _emailError.value = R.string.label_error_field_empty
            return false
        }

        if (email.isEmailFormatIsNotCorrected()) {
            _emailError.value = R.string.label_error_email_no_correct
            return false
        }

        if (phoneNumber.isEmptyOrBlank()) {
            _phoneNumberError.value = R.string.label_error_field_empty
            return false
        }

        if (phoneNumber.isPhoneNumberIsNotCorrected()) {
            _phoneNumberError.value = R.string.label_error_phone_no_correct
            return false
        }
        isUpdateSuccessfully = true
        return true
    }

    fun upDateUser(email: String, phoneNumber: String) {
        _uiState.value = ProfileUIState.Loading
        viewModelScope.launch {
            userUI?.toUser(idUser)?.copy(
                email = email,
                phoneNumber = phoneNumber
            )?.let {
                getUserDataUseCase.upDateUserdata(it)
            }
        }
    }
}