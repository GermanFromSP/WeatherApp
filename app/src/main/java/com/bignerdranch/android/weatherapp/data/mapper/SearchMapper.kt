package com.bignerdranch.android.weatherapp.data.mapper

import com.bignerdranch.android.weatherapp.data.network.dto.CityDto
import com.bignerdranch.android.weatherapp.domain.entity.City

fun CityDto.toEntity(): City = City(id = id, name = name, country = country)

fun List<CityDto>.toEntities(): List<City> = map { it.toEntity() }