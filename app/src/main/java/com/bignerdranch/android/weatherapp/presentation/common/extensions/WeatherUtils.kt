package com.bignerdranch.android.weatherapp.presentation.common.extensions

import android.content.Context
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.domain.entity.WindDirection

fun getWindDirectionImageResId(windDirection: WindDirection): Int = when(windDirection) {
    WindDirection.N -> R.drawable.wind_n
    WindDirection.NNE -> R.drawable.wind_nne
    WindDirection.NE -> R.drawable.wind_ne
    WindDirection.ENE -> R.drawable.wind_ene
    WindDirection.E -> R.drawable.wind_e
    WindDirection.ESE -> R.drawable.wind_ese
    WindDirection.SE -> R.drawable.wind_se
    WindDirection.SSE -> R.drawable.wind_sse
    WindDirection.S -> R.drawable.wind_s
    WindDirection.SSW -> R.drawable.wind_ssw
    WindDirection.SW -> R.drawable.wind_sw
    WindDirection.WSW -> R.drawable.wind_wsw
    WindDirection.W -> R.drawable.wind_w
    WindDirection.WNW -> R.drawable.wind_wnw
    WindDirection.NW -> R.drawable.wind_nw
    WindDirection.NNW -> R.drawable.wind_nnw
    WindDirection.NULL -> R.drawable.wind_failed
}

fun getDayWeatherImage(conditionText: String, context: Context): Int {

    val conditions = mapOf(
        context.getString(R.string.ws_sunny) to R.drawable.sunny,
        context.getString(R.string.ws_party_cloudy) to R.drawable.cloudy_day,
        context.getString(R.string.ws_cloudy) to R.drawable.cloudy_day,
        context.getString(R.string.ws_overcast) to R.drawable.overcast_day,
        context.getString(R.string.ws_mist) to R.drawable.mist_day,
        context.getString(R.string.ws_patchy_rain_nearby) to R.drawable.rain_day,
        context.getString(R.string.ws_patchy_snow_nearby) to R.drawable.light_snow_day,
        context.getString(R.string.ws_patchy_sleet_nearby) to R.drawable.light_day_snow_rain,
        context.getString(R.string.ws_patchy_freezing_drizzle_nearby) to R.drawable.light_day_snow_rain,
        context.getString(R.string.ws_thundery_outbreaks_in_nearby) to R.drawable.storm_day,
        context.getString(R.string.ws_blowing_snow) to R.drawable.light_snow_day,
        context.getString(R.string.ws_blizzard) to R.drawable.heavy_snow_day,
        context.getString(R.string.ws_fog) to R.drawable.mist_day,
        context.getString(R.string.ws_freezing_fog) to R.drawable.mist_day,
        context.getString(R.string.ws_patchy_light_drizzle) to R.drawable.rain_day,
        context.getString(R.string.ws_light_drizzle) to R.drawable.rain_day,
        context.getString(R.string.ws_freezing_drizzle) to R.drawable.light_day_snow_rain,
        context.getString(R.string.ws_heavy_freezing_drizzle) to R.drawable.light_day_snow_rain,
        context.getString(R.string.ws_patchy_light_rain) to R.drawable.rain_day,
        context.getString(R.string.ws_light_rain) to R.drawable.rain_day,
        context.getString(R.string.ws_moderate_rain_at_times) to R.drawable.rain_day,
        context.getString(R.string.ws_moderate_rain) to R.drawable.hard_rain_day,
        context.getString(R.string.ws_heavy_rain_at_times) to R.drawable.hard_rain_day,
        context.getString(R.string.ws_heavy_rain) to R.drawable.hard_rain_day,
        context.getString(R.string.ws_light_freezing_rain) to R.drawable.rain_day,
        context.getString(R.string.ws_moderate_or_heavy_freezing_rain) to R.drawable.hard_rain_day,
        context.getString(R.string.ws_light_sleet) to R.drawable.light_snow_day,
        context.getString(R.string.ws_moderate_or_heavy_sleet) to R.drawable.heavy_snow_day,
        context.getString(R.string.ws_patchy_light_snow) to R.drawable.light_snow_day,
        context.getString(R.string.ws_light_snow) to R.drawable.light_snow_day,
        context.getString(R.string.ws_patchy_moderate_snow) to R.drawable.light_snow_day,
        context.getString(R.string.ws_moderate_snow) to R.drawable.light_snow_day,
        context.getString(R.string.ws_patchy_heavy_snow) to R.drawable.heavy_snow_day,
        context.getString(R.string.ws_heavy_snow) to R.drawable.heavy_snow_day,
        context.getString(R.string.ws_light_rain_shower) to R.drawable.hard_rain_day,
        context.getString(R.string.ws_light_rain_shower) to R.drawable.hard_rain_day,
        context.getString(R.string.ws_ice_pellets) to R.drawable.hard_rain_day,
        context.getString(R.string.ws_moderate_or_heavy_rain_shower) to R.drawable.hard_rain_day,
        context.getString(R.string.ws_torrential_rain_shower) to R.drawable.hard_rain_day,
        context.getString(R.string.ws_light_sleet_showers) to R.drawable.hard_rain_day,
        context.getString(R.string.ws_moderate_or_heavy_sleet_showers) to R.drawable.hard_rain_day,
        context.getString(R.string.ws_light_snow_showers) to R.drawable.heavy_snow_day,
        context.getString(R.string.ws_moderate_or_heavy_snow_showers) to R.drawable.heavy_snow_day,
        context.getString(R.string.ws_light_showers_of_ice_pellets) to R.drawable.light_snow_day,
        context.getString(R.string.ws_moderate_or_heavy_showers_of_ice_pellets) to R.drawable.hard_rain_day,
        context.getString(R.string.ws_patchy_light_rain_in_area_with_thunder) to R.drawable.storm_day,
        context.getString(R.string.ws_moderate_or_heavy_rain_in_area_with_thunder) to R.drawable.storm_day,
        context.getString(R.string.ws_patchy_light_snow_in_area_with_thunder) to R.drawable.heavy_snow_day,
        context.getString(R.string.ws_moderate_or_heavy_snow_in_area_with_thunder) to R.drawable.heavy_snow_day,
        context.getString(R.string.ws_moderate_or_heavy_rain_with_thunder) to R.drawable.storm_day
    )

    return conditions[conditionText] ?: R.drawable.sunny
}

fun getNightWeatherConditionState(conditionText: String, context: Context): Int {

    val conditions = mapOf(
        context.getString(R.string.ws_clear) to R.drawable.clear_night,
        context.getString(R.string.ws_party_cloudy) to R.drawable.cloudy_night,
        context.getString(R.string.ws_cloudy) to R.drawable.cloudy_night,
        context.getString(R.string.ws_overcast) to R.drawable.overcast_night,
        context.getString(R.string.ws_mist) to R.drawable.mist_night,
        context.getString(R.string.ws_patchy_rain_nearby) to R.drawable.light_rain_night,
        context.getString(R.string.ws_patchy_snow_nearby) to R.drawable.light_snow_night,
        context.getString(R.string.ws_patchy_sleet_nearby) to R.drawable.light_day_snow_rain,
        context.getString(R.string.ws_patchy_freezing_drizzle_nearby) to R.drawable.light_day_snow_rain,
        context.getString(R.string.ws_thundery_outbreaks_in_nearby) to R.drawable.storm_night,
        context.getString(R.string.ws_blowing_snow) to R.drawable.light_snow_night,
        context.getString(R.string.ws_blizzard) to R.drawable.heavy_snow_night,
        context.getString(R.string.ws_fog) to R.drawable.mist_night,
        context.getString(R.string.ws_freezing_fog) to R.drawable.mist_night,
        context.getString(R.string.ws_patchy_light_drizzle) to R.drawable.light_rain_night,
        context.getString(R.string.ws_light_drizzle) to R.drawable.light_rain_night,
        context.getString(R.string.ws_freezing_drizzle) to R.drawable.light_day_snow_rain,
        context.getString(R.string.ws_heavy_freezing_drizzle) to R.drawable.light_day_snow_rain,
        context.getString(R.string.ws_patchy_light_rain) to R.drawable.light_rain_night,
        context.getString(R.string.ws_light_rain) to R.drawable.light_rain_night,
        context.getString(R.string.ws_moderate_rain_at_times) to R.drawable.light_rain_night,
        context.getString(R.string.ws_moderate_rain) to R.drawable.hard_rain_night,
        context.getString(R.string.ws_heavy_rain_at_times) to R.drawable.hard_rain_night,
        context.getString(R.string.ws_heavy_rain) to R.drawable.hard_rain_night,
        context.getString(R.string.ws_light_freezing_rain) to R.drawable.light_rain_night,
        context.getString(R.string.ws_moderate_or_heavy_freezing_rain) to R.drawable.hard_rain_night,
        context.getString(R.string.ws_light_sleet) to R.drawable.light_snow_night,
        context.getString(R.string.ws_moderate_or_heavy_sleet) to R.drawable.heavy_snow_night,
        context.getString(R.string.ws_patchy_light_snow) to R.drawable.light_snow_night,
        context.getString(R.string.ws_light_snow) to R.drawable.light_snow_night,
        context.getString(R.string.ws_patchy_moderate_snow) to R.drawable.light_snow_night,
        context.getString(R.string.ws_moderate_snow) to R.drawable.light_snow_night,
        context.getString(R.string.ws_patchy_heavy_snow) to R.drawable.heavy_snow_night,
        context.getString(R.string.ws_heavy_snow) to R.drawable.heavy_snow_night,
        context.getString(R.string.ws_light_rain_shower) to R.drawable.hard_rain_night,
        context.getString(R.string.ws_light_rain_shower) to R.drawable.hard_rain_night,
        context.getString(R.string.ws_ice_pellets) to R.drawable.hard_rain_night,
        context.getString(R.string.ws_moderate_or_heavy_rain_shower) to R.drawable.hard_rain_night,
        context.getString(R.string.ws_torrential_rain_shower) to R.drawable.hard_rain_night,
        context.getString(R.string.ws_light_sleet_showers) to R.drawable.hard_rain_night,
        context.getString(R.string.ws_moderate_or_heavy_sleet_showers) to R.drawable.hard_rain_night,
        context.getString(R.string.ws_light_snow_showers) to R.drawable.heavy_snow_night,
        context.getString(R.string.ws_moderate_or_heavy_snow_showers) to R.drawable.heavy_snow_night,
        context.getString(R.string.ws_light_showers_of_ice_pellets) to R.drawable.light_snow_night,
        context.getString(R.string.ws_moderate_or_heavy_showers_of_ice_pellets) to R.drawable.hard_rain_night,
        context.getString(R.string.ws_patchy_light_rain_in_area_with_thunder) to R.drawable.storm_night,
        context.getString(R.string.ws_moderate_or_heavy_rain_in_area_with_thunder) to R.drawable.storm_night,
        context.getString(R.string.ws_patchy_light_snow_in_area_with_thunder) to R.drawable.heavy_snow_night,
        context.getString(R.string.ws_moderate_or_heavy_snow_in_area_with_thunder) to R.drawable.heavy_snow_night,
        context.getString(R.string.ws_moderate_or_heavy_rain_with_thunder) to R.drawable.storm_night
    )

    return conditions[conditionText] ?: R.drawable.sunny
}