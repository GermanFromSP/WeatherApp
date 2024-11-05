package com.bignerdranch.android.weatherapp.domain.repository

import com.bignerdranch.android.weatherapp.domain.entity.Forecast
import com.bignerdranch.android.weatherapp.domain.entity.CurrentWeather

interface WeatherRepository {

    suspend fun getWeather(cityName: String): CurrentWeather

    suspend fun getForecast(cityName: String, daysCount: Int): Forecast

}