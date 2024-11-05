package com.bignerdranch.android.weatherapp.domain.entity

data class Forecast(
    val localTime: String,
    val currentWeather: CurrentWeather,
    val currentHourlyForecast: List<WeatherInHour>,
    val upcoming: List<ForecastWeather>
)
