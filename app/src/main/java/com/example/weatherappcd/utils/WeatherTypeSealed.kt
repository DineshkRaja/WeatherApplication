package com.example.weatherappcd.utils

import androidx.annotation.DrawableRes
import com.example.weatherappcd.R

sealed class WeatherTypeSealed(
    val weatherDesc: String,
    @DrawableRes val iconRes: Int
) {
    object ClearSky : WeatherTypeSealed(
        weatherDesc = "Clear sky",
        iconRes = R.drawable.ic_sunny
    )

    object MainlyClear : WeatherTypeSealed(
        weatherDesc = "Mainly clear",
        iconRes = R.drawable.ic_cloudy
    )

    object PartlyCloudy : WeatherTypeSealed(
        weatherDesc = "Partly cloudy",
        iconRes = R.drawable.ic_cloudy
    )

    object Overcast : WeatherTypeSealed(
        weatherDesc = "Overcast",
        iconRes = R.drawable.ic_cloudy
    )

    object Foggy : WeatherTypeSealed(
        weatherDesc = "Foggy",
        iconRes = R.drawable.ic_very_cloudy
    )

    object DepositingRimeFog : WeatherTypeSealed(
        weatherDesc = "Depositing rime fog",
        iconRes = R.drawable.ic_very_cloudy
    )

    object LightDrizzle : WeatherTypeSealed(
        weatherDesc = "Light drizzle",
        iconRes = R.drawable.ic_rainshower
    )

    object ModerateDrizzle : WeatherTypeSealed(
        weatherDesc = "Moderate drizzle",
        iconRes = R.drawable.ic_rainshower
    )

    object DenseDrizzle : WeatherTypeSealed(
        weatherDesc = "Dense drizzle",
        iconRes = R.drawable.ic_rainshower
    )

    object LightFreezingDrizzle : WeatherTypeSealed(
        weatherDesc = "Slight freezing drizzle",
        iconRes = R.drawable.ic_snowyrainy
    )

    object DenseFreezingDrizzle : WeatherTypeSealed(
        weatherDesc = "Dense freezing drizzle",
        iconRes = R.drawable.ic_snowyrainy
    )

    object SlightRain : WeatherTypeSealed(
        weatherDesc = "Slight rain",
        iconRes = R.drawable.ic_rainy
    )

    object ModerateRain : WeatherTypeSealed(
        weatherDesc = "Rainy",
        iconRes = R.drawable.ic_rainy
    )

    object HeavyRain : WeatherTypeSealed(
        weatherDesc = "Heavy rain",
        iconRes = R.drawable.ic_rainy
    )

    object HeavyFreezingRain : WeatherTypeSealed(
        weatherDesc = "Heavy freezing rain",
        iconRes = R.drawable.ic_snowyrainy
    )

    object SlightSnowFall : WeatherTypeSealed(
        weatherDesc = "Slight snow fall",
        iconRes = R.drawable.ic_snowy
    )

    object ModerateSnowFall : WeatherTypeSealed(
        weatherDesc = "Moderate snow fall",
        iconRes = R.drawable.ic_heavysnow
    )

    object HeavySnowFall : WeatherTypeSealed(
        weatherDesc = "Heavy snow fall",
        iconRes = R.drawable.ic_heavysnow
    )

    object SnowGrains : WeatherTypeSealed(
        weatherDesc = "Snow grains",
        iconRes = R.drawable.ic_heavysnow
    )

    object SlightRainShowers : WeatherTypeSealed(
        weatherDesc = "Slight rain showers",
        iconRes = R.drawable.ic_rainshower
    )

    object ModerateRainShowers : WeatherTypeSealed(
        weatherDesc = "Moderate rain showers",
        iconRes = R.drawable.ic_rainshower
    )

    object ViolentRainShowers : WeatherTypeSealed(
        weatherDesc = "Violent rain showers",
        iconRes = R.drawable.ic_rainshower
    )

    object SlightSnowShowers : WeatherTypeSealed(
        weatherDesc = "Light snow showers",
        iconRes = R.drawable.ic_snowy
    )

    object HeavySnowShowers : WeatherTypeSealed(
        weatherDesc = "Heavy snow showers",
        iconRes = R.drawable.ic_snowy
    )

    object ModerateThunderstorm : WeatherTypeSealed(
        weatherDesc = "Moderate thunderstorm",
        iconRes = R.drawable.ic_thunder
    )

    object SlightHailThunderstorm : WeatherTypeSealed(
        weatherDesc = "Thunderstorm with slight hail",
        iconRes = R.drawable.ic_rainythunder
    )

    object HeavyHailThunderstorm : WeatherTypeSealed(
        weatherDesc = "Thunderstorm with heavy hail",
        iconRes = R.drawable.ic_rainythunder
    )

    companion object {
        fun fromWMO(code: Int): WeatherTypeSealed {
            return when (code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> ClearSky
            }
        }
    }
}