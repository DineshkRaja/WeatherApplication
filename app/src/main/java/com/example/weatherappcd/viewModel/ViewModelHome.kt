package com.example.weatherappcd.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappcd.view.model.WeatherHourlyResponse
import com.example.weatherappcd.networking.repository.WeatherHourlyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelHome @Inject constructor(
    private val weatherHourlyRepository: WeatherHourlyRepository
) : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherHourlyResponse>()
    val weatherData: LiveData<WeatherHourlyResponse> get() = _weatherData

    private val _throwable = MutableLiveData<Throwable>()
    val throwableData: LiveData<Throwable> get() = _throwable

    fun fetchWeatherData(lat: Double, lon: Double) {
        viewModelScope.launch {
            val result = weatherHourlyRepository.fetchWeatherData(lat, lon)

            result.onSuccess {
                _weatherData.value = it
            }
            result.onFailure {
                _throwable.value = it
            }
        }
    }
}