package com.example.weatherappcd.view.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weatherHourly")
data class WeatherHourlyResponse(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("latitude") var latitude: Double? = null,
    @ColumnInfo("longitude") var longitude: Double? = null,
    @ColumnInfo("hourly") var hourly: Hourly? = Hourly()
)

data class Hourly(
    @ColumnInfo("time") var time: ArrayList<String> = arrayListOf(),
    @ColumnInfo("temperature_2m") var temperature_2m: ArrayList<String> = arrayListOf(),
    @ColumnInfo("relative_humidity_2m") var relative_humidity_2m: ArrayList<String> = arrayListOf(),
    @ColumnInfo("weather_code") var weather_code: ArrayList<Int> = arrayListOf(),
    @ColumnInfo("pressure_msl") var pressure_msl: ArrayList<String> = arrayListOf(),
    @ColumnInfo("wind_speed_10m") var wind_speed_10m: ArrayList<String> = arrayListOf()
)


