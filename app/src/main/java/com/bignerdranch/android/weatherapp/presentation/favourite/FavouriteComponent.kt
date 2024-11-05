package com.bignerdranch.android.weatherapp.presentation.favourite

import com.bignerdranch.android.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface FavouriteComponent {

    val model: StateFlow<FavouriteStore.State>

    fun onClickSearch()

    fun getLocationWeather(cityName: String)

    fun saveRemovedElementsCache()

    fun onClickRemoveFromFavourite(cityId: Int)

    fun onCityItemClick(city: City)
}