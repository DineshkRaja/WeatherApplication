package com.example.weatherappcd.localDatabase.typeConverters

import androidx.room.TypeConverter
import com.example.weatherappcd.view.model.Hourly
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromHourly(value: String?): Hourly? {
        return Gson().fromJson(value, Hourly::class.java)
    }

    @TypeConverter
    fun toHourly(value: Hourly?): String? {
        return Gson().toJson(value)
    }
}