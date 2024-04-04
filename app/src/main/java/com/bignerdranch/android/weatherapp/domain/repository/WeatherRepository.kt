package com.bignerdranch.android.weatherapp.domain.repository

import com.bignerdranch.android.weatherapp.domain.entity.Forecast
import com.bignerdranch.android.weatherapp.domain.entity.Weather

interface WeatherRepository {

    suspend fun getWeather(cityId: Int): Weather

    suspend fun getForecast(cityId: Int): Forecast

}