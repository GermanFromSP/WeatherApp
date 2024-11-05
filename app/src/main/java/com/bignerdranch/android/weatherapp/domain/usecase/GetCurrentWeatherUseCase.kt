package com.bignerdranch.android.weatherapp.domain.usecase

import com.bignerdranch.android.weatherapp.domain.repository.SearchRepository
import com.bignerdranch.android.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityName: String) = repository.getWeather(cityName)

    suspend fun getForecast(cityName: String, daysCount: Int) = repository.getForecast(cityName, daysCount)
}