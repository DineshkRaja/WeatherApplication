package com.example.weatherappcd.dependencyInjection

import android.app.Application
import com.example.weatherappcd.localDatabase.WeatherRoomDatabase
import com.example.weatherappcd.localDatabase.dao.WeatherHourlyDao
import com.example.weatherappcd.networking.WeatherAPI
import com.example.weatherappcd.networking.repository.WeatherHourlyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

private const val BASE_URL = "https://api.open-meteo.com/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesDataBase(application: Application): WeatherRoomDatabase {
        return WeatherRoomDatabase.getDatabase(application)
    }

    @Provides
    @Singleton
    fun providesWeatherAPI(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesWeatherRepository(
        api: WeatherAPI,
        database: WeatherRoomDatabase
    ): WeatherHourlyRepository {
        return WeatherHourlyRepository(api, database)
    }


}