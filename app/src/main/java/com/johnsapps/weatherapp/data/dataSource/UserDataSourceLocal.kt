package com.johnsapps.weatherapp.data.dataSource

import com.johnsapps.weatherapp.data.local.UserDao
import com.johnsapps.weatherapp.data.local.toUser
import com.johnsapps.weatherapp.domain.models.User
import com.johnsapps.weatherapp.domain.models.toUserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataSourceLocal @Inject constructor(
    private val userDao: UserDao
) {

    fun getUser(): Flow<User?> = userDao.getUser().map { it?.toUser() }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user.toUserEntity())
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user.toUserEntity())
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user.toUserEntity())
    }
}