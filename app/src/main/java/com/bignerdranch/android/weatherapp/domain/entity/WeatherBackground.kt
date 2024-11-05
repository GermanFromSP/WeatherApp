package com.bignerdranch.android.weatherapp.domain.entity

import com.bignerdranch.android.weatherapp.R

interface WeatherBackground {

    val backgroundImage: Int

    data class SunnyDay(
        override val backgroundImage: Int = R.drawable.sunny,
    ) : WeatherBackground

    data class SunnyNight(
        override val backgroundImage: Int = R.drawable.clear_night,
    ) : WeatherBackground

    data class CloudyDay(
        override val backgroundImage: Int = R.drawable.cloudy_day,
    ) : WeatherBackground

    data class CloudyNight(
        override val backgroundImage: Int = R.drawable.cloudy_night,
    ) : WeatherBackground

    data class OvercastDay(
        override val backgroundImage: Int = R.drawable.overcast_day,
    ) : WeatherBackground

    data class OvercastNight(
        override val backgroundImage: Int = R.drawable.overcast_night,
    ) : WeatherBackground

    data class MistDay(
        override val backgroundImage: Int = R.drawable.mist_day,
    ) : WeatherBackground

    data class MistNight(
        override val backgroundImage: Int = R.drawable.mist_night,
    ) : WeatherBackground

    data class LightRainDay(
        override val backgroundImage: Int = R.drawable.rain_day,
    ) : WeatherBackground

    data class LightRainNight(
        override val backgroundImage: Int = R.drawable.light_rain_night,
    ) : WeatherBackground

    data class HeavyRainDay(
        override val backgroundImage: Int = R.drawable.hard_rain_day,
    ) : WeatherBackground

    data class HeavyRainNight(
        override val backgroundImage: Int = R.drawable.hard_rain_night,
    ) : WeatherBackground

    data class LightSnowDay(
        override val backgroundImage: Int = R.drawable.light_snow_day,
    ) : WeatherBackground

    data class LightSnowNight(
        override val backgroundImage: Int = R.drawable.light_snow_night,
    ) : WeatherBackground

    data class HeavySnowDay(
        override val backgroundImage: Int = R.drawable.heavy_snow_day,
    ) : WeatherBackground

    data class HeavySnowNight(
        override val backgroundImage: Int = R.drawable.hard_rain_night,
    ) : WeatherBackground

    data class LightSnowRainDay(
        override val backgroundImage: Int = R.drawable.light_day_snow_rain,
    ) : WeatherBackground

    data class LightSnowRainNight(
        override val backgroundImage: Int = R.drawable.light_day_snow_rain,
    ) : WeatherBackground

    data class HeavySnowRainDay(
        override val backgroundImage: Int = R.drawable.light_day_snow_rain,
    ) : WeatherBackground

    data class HeavySnowRainNight(
        override val backgroundImage: Int = R.drawable.light_day_snow_rain,
    ) : WeatherBackground

    data class BlizzardDay(
        override val backgroundImage: Int = R.drawable.heavy_snow_day,
    ) : WeatherBackground

    data class BlizzardNight(
        override val backgroundImage: Int = R.drawable.heavy_snow_night,
    ) : WeatherBackground

    data class StormDay(
        override val backgroundImage: Int = R.drawable.storm_day,
    ) : WeatherBackground

    data class StormNight(
        override val backgroundImage: Int = R.drawable.storm_night,
    ) : WeatherBackground

}