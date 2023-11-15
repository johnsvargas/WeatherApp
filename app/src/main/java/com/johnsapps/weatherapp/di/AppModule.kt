package com.johnsapps.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.johnsapps.weatherapp.data.local.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        UserDataBase::class.java,
        "weather_db"
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: UserDataBase) = db.getUserDao() // The reason we can implement a Dao for the database
}

