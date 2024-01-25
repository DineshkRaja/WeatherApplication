package com.example.weatherappcd.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappcd.view.model.WeatherHourlyResponse
import com.example.weatherappcd.view.repository.WeatherHourlyRepository
import kotlinx.coroutines.launch

class ViewModelHome : ViewModel() {

    private var weatherHourlyRepository = WeatherHourlyRepository()

    private val _weatherData = MutableLiveData<WeatherHourlyResponse>()
    val weatherData: LiveData<WeatherHourlyResponse> get() = _weatherData

    private val _throwable = MutableLiveData<Throwable>()
    val throwableData: LiveData<Throwable> get() = _throwable

    fun fetchWeatherData() {
        viewModelScope.launch {
            val result = weatherHourlyRepository.fetchWeatherData()

            result.onSuccess {
                _weatherData.value = it
            }
            result.onFailure {
                _throwable.value = it
            }
        }
    }
}