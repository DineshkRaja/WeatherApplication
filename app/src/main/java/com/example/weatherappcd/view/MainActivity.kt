package com.example.weatherappcd.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.weatherappcd.R
import com.example.weatherappcd.databinding.ActivityMainBinding
import com.example.weatherappcd.utils.InternetConnectionCallback
import com.example.weatherappcd.utils.WeatherTypeSealed.Companion.fromWMO
import com.example.weatherappcd.utils.WeatherTypeUtil
import com.example.weatherappcd.viewModel.ViewModelHome
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

const val splashScreenValue = "CLOUD_DESTINATION"

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val sharedPref: SharedPreferences by lazy {
        getSharedPreferences(
            splashScreenValue,
            Context.MODE_PRIVATE
        )
    }
    private var splashScreenBoolean = true

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var internetConnectionCallback: InternetConnectionCallback

    private val viewModelHome: ViewModelHome by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the status bar color as transparent
        window.statusBarColor = Color.TRANSPARENT

        //FLAG_FULLSCREEN
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            splashScreenBoolean
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        runBlocking {
            delay(3000)
            splashScreenBoolean = false
        }

        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        internetConnectionCallback = InternetConnectionCallback()
        connectivityManager.registerDefaultNetworkCallback(internetConnectionCallback)
        internetConnectionCallback.isConnected.observe(this) { isConnected ->
            if (isConnected) {
                Toast.makeText(this, "Internet Available", Toast.LENGTH_LONG).show()
                // Internet is connected
                // Do something when internet is available
                viewModelHome.fetchWeatherData()
            } else {
                Toast.makeText(this, "Internet Not Available", Toast.LENGTH_LONG).show()
                // Internet is not connected
                // Do something when internet is not available
            }
        }
        setonClick()
        observers()
    }

    private fun observers() {
        viewModelHome.weatherData.observe(this) {
            binding.weatherDate.text = it.hourly?.time?.get(0) ?: ""
            val check = fromWMO(it.hourly?.weatherCode?.get(0) ?: 0)
            binding.weatherStatus.text = check.weatherDesc

            binding.weatherAnimation.setAnimation(
                R.raw.rainy_weather
            )
            binding.weatherAnimation.playAnimation()

        }

        viewModelHome.throwableData.observe(this) {

        }
    }

    private fun setonClick() {
        binding.futureDays.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterNetworkCallback(internetConnectionCallback)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.futureDays -> {
                val intent = Intent(this@MainActivity, WeatherDaysActivity::class.java).apply {

                }
                startActivity(intent)
            }
        }
    }
}