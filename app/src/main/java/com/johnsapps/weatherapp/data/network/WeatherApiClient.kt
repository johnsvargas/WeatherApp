package com.johnsapps.weatherapp.data.network

import com.johnsapps.weatherapp.data.models.ResponseWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiClient {
   //forecast.json?&q=48.8567,2.3508&days=1&aqi=yes&alerts=no
    //key=367b003da3ed48a590f205354231411
    @GET("forecast.json")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("days") days: String,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String
    ): ResponseWeather
}