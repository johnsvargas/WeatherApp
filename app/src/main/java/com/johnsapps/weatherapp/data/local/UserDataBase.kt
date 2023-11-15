package com.johnsapps.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDataBase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
}