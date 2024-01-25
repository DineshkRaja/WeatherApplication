package com.example.weatherappcd.networking

import com.example.weatherappcd.view.model.WeatherHourlyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("v1/forecast?hourly=temperature_2m,relative_humidity_2m,weather_code,pressure_msl,wind_speed_10m")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double
    ): Response<WeatherHourlyResponse?>
}