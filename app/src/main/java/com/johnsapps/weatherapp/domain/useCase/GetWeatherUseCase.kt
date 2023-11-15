package com.johnsapps.weatherapp.domain.useCase

import com.johnsapps.weatherapp.data.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend fun getWeather(q: String) = repository.getWeather(q)
}