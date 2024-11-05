package com.bignerdranch.android.weatherapp.presentation.common

import android.content.Context
import android.location.Geocoder
import androidx.compose.runtime.compositionLocalOf
import com.bignerdranch.android.weatherapp.domain.entity.City
import java.util.Locale

data class CurrentLocation(
     val longitude: Double = 0.0,
     val latitude: Double = 0.0,
)

fun getCityFromGeo(lat: Double, lon: Double, context: Context): City {
     val geocoder = Geocoder(context, Locale.getDefault())
     var cityName = ""

     try {
          val address = geocoder.getFromLocation(lat, lon, 1)
          address?.let {
               it.forEach { adr ->
                    if (adr.locality != null && adr.locality.isNotEmpty()) {
                         cityName = adr.locality
                         return City(
                              name = cityName,
                              country = adr.countryName,
                              isCurrentLocation = true
                         )
                    }
               }
          }
     } catch (e: Exception) {
          e.printStackTrace()
     }

     return City(0, "")
}

