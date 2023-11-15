package com.johnsapps.weatherapp.data.repository

import com.johnsapps.weatherapp.data.dataSource.WeatherDataSourceRemote
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val dataSource: WeatherDataSourceRemote
) {
    suspend fun getWeather(q: String) = dataSource.getWeather(q)
}