package com.bignerdranch.android.weatherapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val id: Int = 1000,
    val name: String,
    val isCurrentLocation: Boolean = false,
    val country: String = "",
) : Parcelable
