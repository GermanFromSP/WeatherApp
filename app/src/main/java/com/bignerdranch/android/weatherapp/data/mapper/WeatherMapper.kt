package com.bignerdranch.android.weatherapp.data.mapper


import com.bignerdranch.android.weatherapp.data.network.dto.WeatherCurrentDto
import com.bignerdranch.android.weatherapp.data.network.dto.WeatherDto
import com.bignerdranch.android.weatherapp.data.network.dto.WeatherForecastDto
import com.bignerdranch.android.weatherapp.domain.entity.Forecast
import com.bignerdranch.android.weatherapp.domain.entity.Weather
import java.util.Calendar
import java.util.Date

private const val MILLIS_IN_SECOND = 1000

fun WeatherCurrentDto.toEntity(): Weather = current.toEntity()

fun WeatherDto.toEntity(): Weather = Weather(
    tempC = tempC,
    conditionText = conditionDto.text.correctImageUrl(),
    conditionUrl = conditionDto.iconUrl,
    date = date.toCalendar()
)

fun WeatherForecastDto.toEntity() = Forecast(
    currentWeather = current.toEntity(),
    upcoming = forecastDto.forecastDay.drop(1).map { dayDto ->
        val dayWeatherDto = dayDto.dayWeatherDto
        Weather(
            tempC = dayWeatherDto.tempC,
            conditionUrl = dayWeatherDto.conditionDto.iconUrl.correctImageUrl(),
            conditionText = dayWeatherDto.conditionDto.text,
            date = dayDto.date.toCalendar()
        )
    }
)

private fun Long.toCalendar() = Calendar.getInstance().apply {
    time = Date(this@toCalendar * MILLIS_IN_SECOND)
}

private fun String.correctImageUrl() = "https:$this".replace(
    oldValue = "64x64",
    newValue = "128x128"
)