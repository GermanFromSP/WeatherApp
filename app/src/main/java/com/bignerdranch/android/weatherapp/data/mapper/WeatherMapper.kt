package com.bignerdranch.android.weatherapp.data.mapper

import com.bignerdranch.android.weatherapp.data.network.dto.WeatherCurrentDto
import com.bignerdranch.android.weatherapp.data.network.dto.WeatherDto
import com.bignerdranch.android.weatherapp.data.network.dto.WeatherForecastDto
import com.bignerdranch.android.weatherapp.data.network.dto.WeatherInHourDto
import com.bignerdranch.android.weatherapp.domain.entity.CurrentWeather
import com.bignerdranch.android.weatherapp.domain.entity.Forecast
import com.bignerdranch.android.weatherapp.domain.entity.ForecastWeather
import com.bignerdranch.android.weatherapp.domain.entity.WeatherInHour
import com.bignerdranch.android.weatherapp.domain.entity.WindDirection
import com.bignerdranch.android.weatherapp.presentation.common.extensions.tempToFormattedString
import java.util.Calendar
import java.util.Date
import kotlin.math.roundToInt

private const val MILLIS_IN_SECOND = 1000
private const val CONST_KPH_TO_MPS = 3.6f
private const val MILLIMETER_OF_MERCURY = 0.750064

fun WeatherCurrentDto.toEntity(): CurrentWeather = current.toEntity()

fun WeatherDto.toEntity(): CurrentWeather = CurrentWeather(
    tempC = tempC,
    condition = conditionDto.text,
    conditionUrl = conditionDto.iconUrl.correctImageUrl(),
    date = date.toCalendar(),
    isDay = this.isDay == 1,
    windKph = windKph.toMpsString(),
    windDirection = windDirection.toWindDirection(),
    feelsLikeC = feelsLikeC,
    pressureMmM = pressureMb.toMmM(),
    humidity = humidity,
    windGustKph = gustKph.toMpsString(),
    dewPoint = dewPoint,
    visibilityKm = visibilityKm.roundToInt(),
    precipitationsMm = precipitationsMm.roundToInt()
)

fun WeatherInHourDto.toEntity(): WeatherInHour = WeatherInHour(
    time = time.getCurrentTime(),
    tempC = tempC.tempToFormattedString(),
    conditionUrl = conditionDto.iconUrl.correctImageUrl()
)

fun WeatherForecastDto.toEntity(): Forecast {
    val forecastDay = forecastDto.forecastDay
    val hourlyForecasts = forecastDay.mapIndexed() { index, dayDto ->
        val valuesPerDay = dayDto.dayWeatherDto
        ForecastWeather(
            avgTempC = valuesPerDay.tempC.tempToFormattedString(),
            maxTempC = valuesPerDay.maxTempC.tempToFormattedString(),
            minTempC = valuesPerDay.minTempC.tempToFormattedString(),
            maxWindKph = valuesPerDay.maxWindKph.toMpsString(),
            chanceOfRain = valuesPerDay.chanceOfRain,
            chanceOfSnow = valuesPerDay.chanceOfSnow,
            avgHumidity = valuesPerDay.avgHumidity,
            hourlyForecast = dayDto.hourlyForecast.map { it.toEntity() },
            date = dayDto.date.toCalendar(),
            avgVisibility = valuesPerDay.avgVisibility.roundToInt(),
            conditionImageUrl = if (index == 0) this.current.conditionDto.iconUrl.correctImageUrl()
            else dayDto.dayWeatherDto.conditionDto.iconUrl.correctImageUrl()
        )
    }

    val localTime = locationInfo.localTime.getCurrentTime()

    return Forecast(
        localTime = localTime,
        currentWeather = current.toEntity(),
        currentHourlyForecast = getSortedWeatherByCurrentTime(
            weather = hourlyForecasts,
            localTime = localTime,
            sunset = forecastDay.first().astronomical.sunset,
            sunrise = forecastDay.first().astronomical.sunrise
        ),
        upcoming = hourlyForecasts
    )
}
private fun Float.toMmM(): Int = (this * MILLIMETER_OF_MERCURY).roundToInt()

private fun Float.toMpsString(): String = (this/ CONST_KPH_TO_MPS).roundToInt().toString()

private fun Long.toCalendar() = Calendar.getInstance().apply {
    time = Date(this@toCalendar * MILLIS_IN_SECOND)
}

private fun getSunsetItem(sunsetTime: String): WeatherInHour {
    var sunsetHour = sunsetTime.substringBefore(":").toInt()
    if (sunsetTime.takeLast(2) == "PM") sunsetHour += 12

    val correctHour = if (sunsetHour < 10) "0$sunsetHour" else sunsetHour.toString()

    return WeatherInHour(
        time = correctHour + sunsetTime.dropWhile { it != ':' }.dropLast(2),
        isSunset = true
    )
}

private fun getSunriseItem(sunriseTime: String): WeatherInHour {
    var sunriseHour = sunriseTime.substringBefore(":").toInt()
    if (sunriseTime.takeLast(2) == "PM") sunriseHour += 12

    val correctHour = if (sunriseHour < 10) "0$sunriseHour" else sunriseHour.toString()

    return WeatherInHour(
        time = correctHour + sunriseTime.dropWhile { it != ':' }.dropLast(2),
        isSunrise = true
    )
}

private fun getSortedWeatherByCurrentTime(
    weather: List<ForecastWeather>,
    sunrise: String,
    sunset: String,
    localTime: String
): List<WeatherInHour> {

    val currentDayForecast = weather[0].hourlyForecast
    val nextDayForecast = weather[1].hourlyForecast

    val currentWeather = currentDayForecast.find { it.time == localTime }
    val currentIndex = currentDayForecast.indexOf(currentWeather)

    val sunriseItem = getSunriseItem(sunrise)
    val sunsetItem = getSunsetItem(sunset)

    val result = (currentDayForecast.drop(currentIndex) + nextDayForecast.dropLast(
        nextDayForecast.size - currentIndex
    )).toMutableList()

    val sunsetInterval = result.find { it.time == sunsetItem.time.substringBefore(":") }
    val sunsetIndex = result.indexOf(sunsetInterval)
    result.add(sunsetIndex + 1, sunsetItem)

    val sunriseInterval = result.find { it.time == sunriseItem.time.substringBefore(":") }
    val sunriseIndex = result.indexOf(sunriseInterval)
    result.add(sunriseIndex + 1, sunriseItem)

    return result
}

private fun String.toWindDirection(): WindDirection {
   return when(this) {
        "N" -> WindDirection.N
        "NNE" -> WindDirection.NNE
        "NE" -> WindDirection.NE
        "ENE" -> WindDirection.ENE
        "E" -> WindDirection.E
        "ESE" -> WindDirection.ESE
        "SE" -> WindDirection.SE
        "SSE" -> WindDirection.SSE
        "S" -> WindDirection.S
        "SSW" -> WindDirection.SSW
        "SW" -> WindDirection.SW
        "WSW" -> WindDirection.WNW
        "W" -> WindDirection.W
        "WNW" -> WindDirection.WNW
        "NW" -> WindDirection.NW
        "NNW" -> WindDirection.NNW
       else -> WindDirection.NULL
    }
}

private fun String.getCurrentTime() = this.takeLast(5).substringBefore(":")

private fun String.correctImageUrl() = "https:$this".replace(
    oldValue = "64x64",
    newValue = "128x128"
)