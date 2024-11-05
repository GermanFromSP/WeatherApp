package com.bignerdranch.android.weatherapp.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.router.stack.push
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.WeatherApp
import com.bignerdranch.android.weatherapp.presentation.common.SharedPreferencesService
import com.bignerdranch.android.weatherapp.presentation.common.getCityFromGeo
import com.bignerdranch.android.weatherapp.presentation.root.DefaultRootComponent
import com.bignerdranch.android.weatherapp.presentation.root.RootContent
import com.bignerdranch.android.weatherapp.presentation.search.OpenReason
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var root: DefaultRootComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        val application = applicationContext as WeatherApp

        application.applicationComponent.inject(this)
        super.onCreate(savedInstanceState)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation(application)
        root = rootComponentFactory.create(defaultComponentContext())

        setContent {
            val sharedPreferences = SharedPreferencesService(this)
            var city = sharedPreferences.getData(CITY_KEY, null)


            val locationState by application.location.collectAsState()

            val latitude = locationState.latitude
            val longitude = locationState.longitude

            if (city == null || city.isCurrentLocation ) {
                val currentConfig = root.stack.value.active.configuration
                city = getCityFromGeo(latitude, longitude, this)
                if (currentConfig !is DefaultRootComponent.Config.Details || currentConfig.city != city) {
                    root.navigation.push(DefaultRootComponent.Config.Details(city, true, OpenReason.SeeDetails))
                }
            } else {
                val currentConfig = root.stack.value.active.configuration
                if (currentConfig !is DefaultRootComponent.Config.Details || currentConfig.city != city) {
                    root.navigation.push(DefaultRootComponent.Config.Details(city,true, OpenReason.SeeDetails))
                }
            }

            RootContent(component = root)
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation(application: Application) {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: MutableList<Address>? =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)

                        val latitude = list?.get(0)?.latitude!!
                        val longitude = list[0].longitude
                        (application as WeatherApp).cacheLocation(latitude, longitude)
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.please_turn_on_location), Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            REQUEST_CODE
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation(application)
            }
        }
    }


    companion object {
        private const val REQUEST_CODE = 32
        const val CITY_KEY = "city_key"
        const val SHARED_PREF_NAME = "last_city"
        const val LAST_LOCATION = "last_location"
    }


}

