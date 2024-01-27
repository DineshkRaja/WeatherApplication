package com.example.weatherappcd.view.model

import com.example.weatherappcd.utils.WeatherTypeSealed
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class IndexedWeatherData(
    val index: Int,
    val data: WeatherModelClass
)

object WeatherMappers {
    private fun toWeatherModelMap(hourly: Hourly): Map<Int, List<WeatherModelClass>> {

        return hourly.time.mapIndexed { index, time ->
            val temperature = hourly.temperature_2m[index]
            val weatherCode = hourly.weather_code[index]
            val windSpeed = hourly.wind_speed_10m[index]
            val pressure = hourly.pressure_msl[index]
            val humidity = hourly.relative_humidity_2m[index]
            IndexedWeatherData(
                index = index,
                data = WeatherModelClass(
                    time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                    temperature = temperature,
                    pressure = pressure,
                    windSpeed = windSpeed,
                    humidity = humidity,
                    weatherTypes = WeatherTypeSealed.fromWMO(weatherCode),
                    weatherCode = weatherCode
                )
            )
        }.groupBy {
            it.index / 24
        }.mapValues { it ->
            it.value.map {
                it.data
            }
        }

    }

    fun toWeatherInfo(hourly: Hourly): WeatherInfo? {
        val weatherDataMap = toWeatherModelMap(hourly)
        val now = LocalDateTime.now()
        val currentWeatherData = weatherDataMap[0]?.find {
            val hour = if (now.minute < 30) now.hour else now.hour + 1
            it.time.hour == if (hour > 23) 0 else hour
        }
        if (currentWeatherData != null) {
            return WeatherInfo(
                weatherPerDay = weatherDataMap,
                currentWeather = currentWeatherData
            )
        }
        return null
    }

    fun toWeatherObject(weatherList: List<WeatherModelClass>, position : Int) : WeatherModelClass{
        val now = LocalDateTime.now()
        val weatherModelClass = weatherList.find {
            val hour = if (now.minute < 30) now.hour else now.hour + 1
            it.time.hour == if (hour > 23) 0 else hour
        } ?: return weatherList[position]
        return weatherModelClass
    }
}

