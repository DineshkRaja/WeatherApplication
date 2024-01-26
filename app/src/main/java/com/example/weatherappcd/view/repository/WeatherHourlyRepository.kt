package com.example.weatherappcd.view.repository

import com.example.weatherappcd.networking.RetrofitBuilder
import com.example.weatherappcd.view.model.WeatherHourlyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException

class WeatherHourlyRepository {
    suspend fun fetchWeatherData(lat: Double, lon: Double): Result<WeatherHourlyResponse?> =
        coroutineScope {
            try {
                val deferredResult = async(Dispatchers.IO) {
                    RetrofitBuilder.apiService.getWeatherData(
                        lat,
                        lon
                    )
                }
                Result.success(deferredResult.await().body())
            } catch (e: HttpException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}