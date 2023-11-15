package com.johnsapps.weatherapp.data.dataSource

import com.johnsapps.weatherapp.data.models.toWeatherItem
import com.johnsapps.weatherapp.data.network.WeatherApiClient
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherDataSourceRemote @Inject constructor(
    private val apiClient: WeatherApiClient
) {
    private val key = "367b003da3ed48a590f205354231411"

    suspend fun getWeather(q: String) = flow {
        emit(apiClient.getWeather(
            key = key,
            q = q,
            days = "1",
            aqi = "yes",
            alerts = "no"
        ).toWeatherItem())
    }
}