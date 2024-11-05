package com.bignerdranch.android.weatherapp.presentation.search

import com.bignerdranch.android.weatherapp.domain.entity.City
import com.bignerdranch.android.weatherapp.domain.entity.CurrentWeather

data class FoundCityItem(
    val city: City,
    val currentWeather: CurrentWeather
)
