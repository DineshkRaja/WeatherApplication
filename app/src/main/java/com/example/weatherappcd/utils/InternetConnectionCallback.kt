package com.example.weatherappcd.utils

import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class InternetConnectionCallback : ConnectivityManager.NetworkCallback() {

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> get() = _isConnected

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        _isConnected.postValue(true)
        Log.d("InternetCheck", "Internet is available.")
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        _isConnected.postValue(false)
        Log.d("InternetCheck", "Internet is not available.")
    }
}
