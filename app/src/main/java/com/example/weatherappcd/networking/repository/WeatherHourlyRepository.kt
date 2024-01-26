package com.example.weatherappcd.networking.repository

import com.example.weatherappcd.localDatabase.WeatherRoomDatabase
import com.example.weatherappcd.networking.WeatherAPI
import com.example.weatherappcd.view.model.WeatherHourlyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class WeatherHourlyRepository @Inject constructor(
    private val api: WeatherAPI,
    private val database: WeatherRoomDatabase
) {
    suspend fun fetchWeatherData(lat: Double, lon: Double): Result<WeatherHourlyResponse?> =
        coroutineScope {
            try {
                val deferredResult = async(Dispatchers.IO) {
                    api.getWeatherData(
                        lat,
                        lon
                    )
                }
                saveIntoRoom(deferredResult.await().body())
                Result.success(deferredResult.await().body())
            } catch (e: HttpException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            } catch (e: UnknownHostException) {
                Result.failure(e)
            }
        }

    private suspend fun saveIntoRoom(weatherHourlyResponse: WeatherHourlyResponse?) {
        if (weatherHourlyResponse != null) {
            database.getWeatherHourlyDao().insert(weatherHourlyResponse)
        }
    }
}