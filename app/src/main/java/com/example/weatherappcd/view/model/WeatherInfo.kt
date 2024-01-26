package com.example.weatherappcd.view.model

data class WeatherInfo(
    val weatherPerDay: Map<Int, List<WeatherModelClass>>,
    val currentWeather: WeatherModelClass
)