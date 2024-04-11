package com.bignerdranch.android.weatherapp.presentation.details

import kotlinx.coroutines.flow.StateFlow

interface DetailsComponent {

    val model: StateFlow<DetailStore.State>

    fun onClickBack()

    fun onClickChangeFavouriteStatus()
}