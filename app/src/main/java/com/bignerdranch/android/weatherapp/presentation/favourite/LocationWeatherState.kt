package com.bignerdranch.android.weatherapp.presentation.favourite

import com.bignerdranch.android.weatherapp.domain.entity.CurrentWeather

sealed interface LocationWeatherState {

    data object Initial : LocationWeatherState

    data class Success(val weather: CurrentWeather) : LocationWeatherState

    data object Loading : LocationWeatherState

    data object Error : LocationWeatherState

}
