package com.example.weatherappcd.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.weatherappcd.R
import com.example.weatherappcd.databinding.ActivityWeatherDaysBinding

class WeatherDaysActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bindingForWeatherDays: ActivityWeatherDaysBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.TRANSPARENT
        //FLAG_FULLSCREEN
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        bindingForWeatherDays = ActivityWeatherDaysBinding.inflate(layoutInflater)
        val view = bindingForWeatherDays.root
        setContentView(view)

        onClickWidget()
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