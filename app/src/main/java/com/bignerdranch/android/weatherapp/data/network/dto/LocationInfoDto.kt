package com.bignerdranch.android.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class LocationInfoDto(
   @SerializedName("localtime") val localTime: String
)
