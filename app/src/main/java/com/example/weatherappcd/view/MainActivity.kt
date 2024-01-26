package com.example.weatherappcd.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.*
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.weatherappcd.R
import com.example.weatherappcd.databinding.ActivityMainBinding
import com.example.weatherappcd.utils.CommonUtils.getAddressFromLocation
import com.example.weatherappcd.utils.InternetConnectionCallback
import com.example.weatherappcd.utils.LottieProgressDialog
import com.example.weatherappcd.utils.WeatherTypeSealed.Companion.fromWMO
import com.example.weatherappcd.viewModel.ViewModelHome
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


const val MAIN_PREFERENCE = "CLOUD_DESTINATION"

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val sharedPref: SharedPreferences by lazy {
        getSharedPreferences(
            MAIN_PREFERENCE,
            Context.MODE_PRIVATE
        )
    }
    private var splashScreenBoolean = true

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var internetConnectionCallback: InternetConnectionCallback

    private val viewModelHome: ViewModelHome by viewModels()

    private lateinit var progressDialog: LottieProgressDialog

    private var lat = 0.0
    private var lon = 0.0

    //Location Process
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationManager: LocationManager
    private var builderGPS: AlertDialog? = null
    private var builderLocation: AlertDialog? = null

    //Location permission request process
    private var requestPermissionLauncher: ActivityResultLauncher<Array<String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleStatusBar()
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { splashScreenBoolean }
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        runBlocking {
            delay(3000)
            splashScreenBoolean = false
        }

        handleLocation()
        initializeView()
        setonClick()
        observers()
        handleConnection()
    }

    override fun onResume() {
        super.onResume()
        if (hasLocationPhonePermission()) {
            startLocationUpdates()
        } else {
            showSettingsDialog()
        }
        showLocationGPSonDialog()
    }

    override fun onPause() {
        super.onPause()
        //For Avoid Memory Leak
        builderGPS?.dismiss()
        builderLocation?.dismiss()
    }

    override fun onStop() {
        super.onStop()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun handleConnection() {
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        internetConnectionCallback = InternetConnectionCallback()
        connectivityManager.registerDefaultNetworkCallback(internetConnectionCallback)
        internetConnectionCallback.isConnected.observe(this) { isConnected ->
            if (isConnected) {
                if (lat != 0.0 || lon != 0.0) {
                    viewModelHome.fetchWeatherData(lat, lon)
                }
            }
        }
    }

    private fun handleStatusBar() {
        // Set the status bar color as transparent
        window.statusBarColor = Color.TRANSPARENT
        //FLAG_FULLSCREEN
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun initializeView() {
        progressDialog = LottieProgressDialog(
            context = this@MainActivity,
            isCancel = true,
            dialogWidth = null,
            dialogHeight = null,
            animationViewWidth = null,
            animationViewHeight = null,
            fileName = LottieProgressDialog.WEATHER_LOADING,
            title = null,
            titleVisible = null,
            okayButtonVisibility = true
        )
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
        binding.weatherLocation.setOnClickListener(this)
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

            R.id.weatherLocation -> {
                redirectionGoogleMaps()
            }
        }
    }


    //Location Handles Need to Optimize it
    private fun handleLocation() {
        //Location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)

        locationRequest = LocationRequest.Builder(Priority.PRIORITY_LOW_POWER, 30000)
            .setWaitForAccurateLocation(false)
            .build()

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //Location Callback Function
        locationCallback = object : LocationCallback() {
            @SuppressLint("SetTextI18n")
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult != null) {
                    if (locationResult.locations != null) {
                        if (locationResult.locations.isNotEmpty()) {
                            for (location in locationResult.locations) {
                                lat = location.latitude
                                lon = location.longitude
                                binding.weatherLocation.text =
                                    getAddressFromLocation(this@MainActivity, location)
                            }
                        } else if (locationResult.lastLocation != null) {
                            lat = locationResult.lastLocation!!.latitude
                            lon = locationResult.lastLocation!!.longitude
                            binding.weatherLocation.text = getAddressFromLocation(
                                this@MainActivity,
                                locationResult.lastLocation!!
                            )
                        }
                    } else if (locationResult.lastLocation != null) {
                        lat = locationResult.lastLocation!!.latitude
                        lon = locationResult.lastLocation!!.longitude
                        binding.weatherLocation.text =
                            getAddressFromLocation(this@MainActivity, locationResult.lastLocation!!)
                    }
                }

                viewModelHome.fetchWeatherData(lat, lon)
            }
        }


        //Location Permission check
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                permissions.entries.forEach {
                    if (it.key == "android.permission.ACCESS_FINE_LOCATION") {
                        if (it.value) {
                            startLocationUpdates()
                        } else {
                            requestLocationPermission()
                        }
                    }
                }
            }
    }

    private fun startLocationUpdates() {
        if (checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            &&
            checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest, locationCallback, null
            )
        } else {
            requestLocationPermission()
        }
    }

    private fun showSettingsDialog() {
        if (builderLocation?.isShowing == true) {
            builderLocation?.dismiss()
        }
        builderLocation = AlertDialog.Builder(this@MainActivity).apply {
            setTitle("Permission needed")
            setMessage("Need to location permission for detect weather")
            setIcon(R.drawable.ic_cloudy)
            setPositiveButton("Go to settings") { dialog: DialogInterface, which: Int ->
                dialog.cancel()
                openSettings()
            }
            setNegativeButton(
                getString(android.R.string.cancel)
            ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
        }.create()
        builderLocation?.show()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun showLocationGPSonDialog() {
        if (builderGPS?.isShowing == true) {
            builderGPS?.dismiss()
        }
        //&& !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (builderGPS == null) {
                builderGPS = AlertDialog.Builder(this).apply {
                    setTitle("Location Services")
                    setMessage("For a better experience, turn on GPS device location, which uses Google's location service.")
                    setPositiveButton("Turn on Location") { dialog, which ->
                        dialog.dismiss()
                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                    setNegativeButton("No Thanks") { dialog, which ->
                        dialog.dismiss()
                    }
                }.create()

                builderGPS?.show()
            }
        }
    }

    private fun hasLocationPhonePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun redirectionGoogleMaps() {
        if (lat != 0.0 || lon != 0.0) {
            val gmmIntentUri = Uri.parse("http://maps.google.com/maps?q=$lat,$lon")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.resolveActivity(packageManager)?.let {
                startActivity(mapIntent)
            }
        }
    }

    private fun requestLocationPermission() {
        requestPermissionLauncher?.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}