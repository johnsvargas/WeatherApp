package com.johnsapps.weatherapp.data.models

import com.google.gson.annotations.SerializedName
import com.johnsapps.weatherapp.domain.models.WeatherItem

data class ResponseWeather(
    val location: Location,
    val current: Current,
    val forecast: Forecast
)

data class Location(
    val name: String,
    val country: String
)

data class Current(
    @SerializedName("temp_c")
    val tempC: Float,
    @SerializedName("wind_kph")
    val windKph: Float,
    @SerializedName("wind_degree")
    val windDegree: Int
)

data class Forecast(
    val forecastday: List<Forecastday>
)

data class Forecastday(
    val day: Day,
    val astro: Astro,
)

data class Day(
    @SerializedName("maxtemp_c")
    val maxTempC: Float,
    @SerializedName("mintemp_c")
    val minTempC: Float,
)

data class Astro(
    val sunrise: String,
    val sunset: String
)

fun ResponseWeather.toWeatherItem(): WeatherItem {
    val forecastday = this.forecast.forecastday.first()
    return WeatherItem(
    name = this.location.name,
    country = this.location.country,
    currentTemp = this.current.tempC,
    currentWindKph = this.current.windKph,
    currentDegree = this.current.windDegree,
    maxTemp = forecastday.day.maxTempC,
    minTemp = forecastday.day.minTempC,
    sunRise = forecastday.astro.sunrise,
    sunSet = forecastday.astro.sunset
    )
}