package com.example.weatherappcd.utils

import com.example.weatherappcd.R
object WeatherTypeUtil {

    //Animation
    fun getWeatherAnimation(weatherCode: Int): Int {
        return when (weatherCode) {
            0 -> R.raw.clear_day
            1 -> R.raw.few_clouds
            2 -> R.raw.few_clouds
            3 -> R.raw.few_clouds
            45 -> R.raw.mostly_cloudy
            48 -> R.raw.mostly_cloudy
            51 -> R.raw.shower_rain
            53 -> R.raw.shower_rain
            55 -> R.raw.shower_rain
            56 -> R.raw.snow_weather
            57 -> R.raw.snow_weather
            61 -> R.raw.rainy_weather
            63 -> R.raw.rainy_weather
            65 -> R.raw.rainy_weather
            66 -> R.raw.snow_lightly
            67 -> R.raw.snow_lightly
            71 -> R.raw.snow_weather
            73 -> R.raw.snow_weather
            75 -> R.raw.snow_weather
            77 -> R.raw.snow_weather
            80 -> R.raw.shower_rain
            81 -> R.raw.shower_rain
            82 -> R.raw.shower_rain
            85 -> R.raw.snow_lightly
            86 -> R.raw.snow_weather
            95 -> R.raw.thunder
            96 -> R.raw.thunder
            99 -> R.raw.rainy_weather
            else -> R.raw.clear_day
        }
    }

}