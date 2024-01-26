package com.example.weatherappcd.view

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.weatherappcd.R
import com.example.weatherappcd.databinding.ActivityWeatherDaysBinding
import com.example.weatherappcd.localDatabase.WeatherRoomDatabase
import com.example.weatherappcd.utils.WeatherTypeUtil
import com.example.weatherappcd.view.adapters.WeeklyWeatherAdapter
import com.example.weatherappcd.view.model.WeatherMappers
import com.example.weatherappcd.view.model.WeatherModelClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter
import java.util.Locale


class WeatherDaysActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bindingForWeatherDays: ActivityWeatherDaysBinding
    private val database by lazy { WeatherRoomDatabase.getDatabase(applicationContext) }
    private var weeklyWeatherAdapter: WeeklyWeatherAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingForWeatherDays = ActivityWeatherDaysBinding.inflate(layoutInflater)
        val view = bindingForWeatherDays.root
        setContentView(view)
        val listener = { weatherModelData: WeatherModelClass ->
            bindingForWeatherDays.constraintLayout.startAnimation(
                AnimationUtils.loadAnimation(
                    applicationContext,
                    android.R.anim.fade_out
                )
            )
            setWidgetValues(weatherModelData)
        }

        weeklyWeatherAdapter = WeeklyWeatherAdapter(this@WeatherDaysActivity, listener)
        bindingForWeatherDays.weatherDaysRecycler.adapter = weeklyWeatherAdapter

        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Default) {
                if (database != null && database.getWeatherHourlyDao() != null && database.getWeatherHourlyDao()
                        .getAllWeatherHourly() != null && database.getWeatherHourlyDao()
                        .getAllWeatherHourly().hourly != null
                ) {
                    val values = WeatherMappers.toWeatherInfo(
                        database.getWeatherHourlyDao().getAllWeatherHourly().hourly!!
                    )
                    withContext(Dispatchers.Main) {
                        onClickWidget()
                        if (values != null && values.currentWeather != null) {
                            setWidgetValues(values.currentWeather)
                        }
                        if (weeklyWeatherAdapter != null && values?.weatherPerDay != null) {
                            weeklyWeatherAdapter?.setDailyWeatherData(values.weatherPerDay)
                        }
                    }
                }
            }
        }
    }

    private fun setWidgetValues(currentWeather: WeatherModelClass) {
        if (currentWeather.time != null) {
            bindingForWeatherDays.selectedDate.text =
                currentWeather.time.format(DateTimeFormatter.ofPattern("EEEE", Locale.getDefault()))
        }
        if (!currentWeather.temperature.isNullOrEmpty()) {
            bindingForWeatherDays.selectedTemp.text = "${currentWeather.temperature}°C"
        }
        if (!currentWeather.weatherTypes.weatherDesc.isNullOrEmpty()) {
            bindingForWeatherDays.selectedWeatherStatus.text =
                currentWeather.weatherTypes.weatherDesc
        }
        if (WeatherTypeUtil.getWeatherAnimation(currentWeather.weatherCode) != null) {
            bindingForWeatherDays.futureWeatherAnimation.setAnimation(
                WeatherTypeUtil.getWeatherAnimation(
                    currentWeather.weatherCode
                )
            )
            bindingForWeatherDays.futureWeatherAnimation.playAnimation()
        }
        bindingForWeatherDays.humidityText.text = "${currentWeather.humidity}%" ?: "N/A"
        bindingForWeatherDays.windText.text = "${currentWeather.windSpeed} km/h" ?: "N/A"
        bindingForWeatherDays.seaLevelText.text = "${currentWeather.pressure}hpa" ?: "N/A"
    }

    private fun onClickWidget() {
        bindingForWeatherDays.weatherDaysBack.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.weatherDaysBack -> {
                finish()
            }
        }
    }
}