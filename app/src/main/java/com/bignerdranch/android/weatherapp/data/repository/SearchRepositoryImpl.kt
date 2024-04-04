package com.bignerdranch.android.weatherapp.data.repository

import com.bignerdranch.android.weatherapp.data.mapper.toEntities
import com.bignerdranch.android.weatherapp.data.mapper.toEntity
import com.bignerdranch.android.weatherapp.data.network.api.ApiService
import com.bignerdranch.android.weatherapp.domain.entity.City
import com.bignerdranch.android.weatherapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {

    override suspend fun search(query: String): List<City> {
        return apiService.searchCity(query).toEntities()
    }
}