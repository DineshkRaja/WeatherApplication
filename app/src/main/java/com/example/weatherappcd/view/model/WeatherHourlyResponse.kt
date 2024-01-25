package com.example.weatherappcd.view.model

import com.google.gson.annotations.SerializedName

data class WeatherHourlyResponse(
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("generationtime_ms") var generationtimeMs: String? = null,
    @SerializedName("utc_offset_seconds") var utcOffsetSeconds: String? = null,
    @SerializedName("timezone") var timezone: String? = null,
    @SerializedName("timezone_abbreviation") var timezoneAbbreviation: String? = null,
    @SerializedName("elevation") var elevation: String? = null,
    @SerializedName("hourly_units") var hourlyUnits: HourlyUnits? = HourlyUnits(),
    @SerializedName("hourly") var hourly: Hourly? = Hourly()
)

data class HourlyUnits(
    @SerializedName("time") var time: String? = null,
    @SerializedName("temperature_2m") var temperature2m: String? = null,
    @SerializedName("relative_humidity_2m") var relativeHumidity2m: String? = null,
    @SerializedName("weather_code") var weatherCode: String? = null,
    @SerializedName("pressure_msl") var pressureMsl: String? = null,
    @SerializedName("wind_speed_10m") var windSpeed10m: String? = null
)

data class Hourly(
    @SerializedName("time") var time: ArrayList<String> = arrayListOf(),
    @SerializedName("temperature_2m") var temperature2m: ArrayList<String> = arrayListOf(),
    @SerializedName("relative_humidity_2m") var relativeHumidity2m: ArrayList<String> = arrayListOf(),
    @SerializedName("weather_code") var weatherCode: ArrayList<Int> = arrayListOf(),
    @SerializedName("pressure_msl") var pressureMsl: ArrayList<String> = arrayListOf(),
    @SerializedName("wind_speed_10m") var windSpeed10m: ArrayList<String> = arrayListOf()
)


