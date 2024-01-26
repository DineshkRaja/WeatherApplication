package com.example.weatherappcd.view.model

import com.example.weatherappcd.utils.WeatherTypeSealed
import java.time.LocalDateTime

data class WeatherModelClass(
    var time : LocalDateTime,
    var temperature: String,
    var humidity: String,
    var weatherTypes: WeatherTypeSealed,
    var weatherCode: Int,
    var pressure: String,
    var windSpeed: String,
)