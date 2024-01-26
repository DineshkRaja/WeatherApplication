package com.example.weatherappcd.localDatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherappcd.view.model.WeatherHourlyResponse

@Dao
interface WeatherHourlyDao {
    @Query("SELECT * FROM weatherHourly")
    suspend fun getAllWeatherHourly(): WeatherHourlyResponse
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherHourlyResponse: WeatherHourlyResponse)
}