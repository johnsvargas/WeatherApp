package com.johnsapps.weatherapp.data.repository

import com.johnsapps.weatherapp.data.dataSource.UserDataSourceLocal
import com.johnsapps.weatherapp.domain.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataSourceLocal: UserDataSourceLocal
) {
    val user: Flow<User?> = dataSourceLocal.getUser()

    suspend fun insertUser(user: User) {
        dataSourceLocal.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        dataSourceLocal.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        dataSourceLocal.deleteUser(user)
    }
}