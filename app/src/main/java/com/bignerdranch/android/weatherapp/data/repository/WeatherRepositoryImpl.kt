package com.bignerdranch.android.weatherapp.data.repository

import com.bignerdranch.android.weatherapp.data.mapper.toEntity
import com.bignerdranch.android.weatherapp.data.network.api.ApiService
import com.bignerdranch.android.weatherapp.domain.entity.Forecast
import com.bignerdranch.android.weatherapp.domain.entity.CurrentWeather
import com.bignerdranch.android.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherRepository {

    override suspend fun getWeather(cityName: String): CurrentWeather {
        return apiService.loadCurrentWeather(cityName).toEntity()
    }

    override suspend fun getForecast(cityName: String, daysCount: Int): Forecast {
        return apiService.loadForecast(cityName, daysCount).toEntity()
    }

    private companion object {

        private const val PREFIX_CITY_ID = "id:"
    }
}