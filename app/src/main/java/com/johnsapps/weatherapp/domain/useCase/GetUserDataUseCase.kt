package com.johnsapps.weatherapp.domain.useCase

import com.johnsapps.weatherapp.data.repository.UserRepository
import com.johnsapps.weatherapp.domain.models.User
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val repository: UserRepository
) {
    val user = repository.user

    suspend fun upDateUserdata(user: User) {
        repository.updateUser(user)
    }

    suspend fun deleteUserData(user: User) {
        repository.deleteUser(user)
    }
}