package com.example.weatherappcd.utils

import android.content.Context
import com.example.weatherappcd.R
import java.util.Calendar
import java.util.Locale

object WeatherTypeUtil {

    //Animation
    fun getWeatherAnimation(weatherCode: Int): Int {
        if (weatherCode / 100 == 2) {
            return R.raw.storm_weather
        } else if (weatherCode / 100 == 3) {
            return R.raw.rainy_weather
        } else if (weatherCode / 100 == 5) {
            return R.raw.rainy_weather
        } else if (weatherCode / 100 == 6) {
            return R.raw.snow_weather
        } else if (weatherCode / 100 == 7) {
            return R.raw.unknown
        } else if (weatherCode == 800) {
            return R.raw.clear_day
        } else if (weatherCode == 801) {
            return R.raw.few_clouds
        } else if (weatherCode == 803) {
            return R.raw.broken_clouds
        } else if (weatherCode / 100 == 8) {
            return R.raw.cloudy_weather
        }
        return R.raw.unknown
    }


    fun getTime(calendar: Calendar, context: Context): String {
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val hourString: String = if (hour < 10) {
            String.format(Locale.getDefault(), "0%d", hour)
        } else {
            String.format(Locale.getDefault(), "%d", hour)
        }
        val minuteString: String = if (minute < 10) {
            String.format(Locale.getDefault(), "0%d", minute)
        } else {
            String.format(Locale.getDefault(), "%d", minute)
        }
        return "$hourString:$minuteString"
    }

}