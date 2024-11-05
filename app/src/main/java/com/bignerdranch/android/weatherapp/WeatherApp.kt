package com.bignerdranch.android.weatherapp

import android.app.Application
import com.bignerdranch.android.weatherapp.di.ApplicationComponent
import com.bignerdranch.android.weatherapp.di.DaggerApplicationComponent
import com.bignerdranch.android.weatherapp.presentation.common.CurrentLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WeatherApp: Application() {

    lateinit var applicationComponent: ApplicationComponent

    private var _location: MutableStateFlow<CurrentLocation> = MutableStateFlow(CurrentLocation())

    val location: StateFlow<CurrentLocation> get() = _location

    fun cacheLocation(lat: Double, lon: Double) {
        _location.value = CurrentLocation(lon, lat)
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}
