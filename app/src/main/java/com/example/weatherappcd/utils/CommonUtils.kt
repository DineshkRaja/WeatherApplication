package com.example.weatherappcd.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.io.IOException
import java.util.Locale

object CommonUtils {
    const val currentWeatherReportData =
        "temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m"

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null &&
                (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }

    fun getAddressFromLocation(context: Context, location: Location): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        var addressText = ""
        try {
            val addresses: List<Address> =
                geocoder.getFromLocation(location.latitude, location.longitude, 1) as List<Address>
            if (addresses.isNotEmpty()) {
                if (addresses[0] != null && !addresses[0].locality.isNullOrEmpty()) {
                    addressText = addresses[0].locality ?: ""
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return addressText
    }
}