package com.bignerdranch.android.weatherapp.domain.repository

import com.bignerdranch.android.weatherapp.domain.entity.City

interface SearchRepository {

    suspend fun search(query: String): List<City>
}