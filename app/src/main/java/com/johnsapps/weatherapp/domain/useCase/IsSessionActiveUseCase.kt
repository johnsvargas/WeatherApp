package com.johnsapps.weatherapp.domain.useCase

import com.johnsapps.weatherapp.data.repository.UserRepository
import com.johnsapps.weatherapp.domain.models.User
import javax.inject.Inject

class IsSessionActiveUseCase @Inject constructor(
    private val repository: UserRepository
) {
    fun getUser() = repository.user

    suspend fun insertUser(name: String) {
        repository.insertUser(
            User(
                0, name, "", ""
            )
        )
    }
}