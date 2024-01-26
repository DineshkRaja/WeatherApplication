package com.example.weatherappcd.localDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherappcd.localDatabase.dao.WeatherHourlyDao
import com.example.weatherappcd.localDatabase.typeConverters.Converters
import com.example.weatherappcd.view.model.WeatherHourlyResponse

@Database(entities = [WeatherHourlyResponse::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherRoomDatabase : RoomDatabase() {
    abstract fun getWeatherHourlyDao(): WeatherHourlyDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherRoomDatabase? = null
        fun getDatabase(context: Context): WeatherRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherRoomDatabase::class.java,
                    "weather_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}