package com.bignerdranch.android.weatherapp.presentation.details.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.domain.entity.WeatherInHour
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun AnimatedHourlyWeather(modifier: Modifier = Modifier, weather: List<WeatherInHour>) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(animationSpec = tween(500)) + slideIn(
            animationSpec = tween(500),
            initialOffset = { IntOffset(0, it.height) }
        )
    ) {
        HourlyWeather(weather = weather)
    }
}


@Composable
fun HourlyWeather(modifier: Modifier = Modifier, weather: List<WeatherInHour>) {

    val textColor = MaterialTheme.colorScheme.onBackground

    DetailsCard(
        titleIcon = Icons.Default.AccessTime,
        titleText = stringResource(R.string.twenty_four_hour_forecast_title),
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 8.dp)
    ) {

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(8.dp)
        ) {
            itemsIndexed(weather) { index, item ->

                val context = LocalContext.current
                val timeText = if (index == 0)
                    context.getString(R.string.current_weather_time)
                else
                    item.time

                Column(
                    modifier = modifier.padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val bottomText: String

                    Text(
                        text = timeText,
                        color = textColor
                    )

                    when {
                        item.isSunset -> {
                            bottomText = context.getString(R.string.weather_sunset)
                            HourlyWeatherIcon(resource = R.drawable.sunset_icon)
                        }

                        item.isSunrise -> {
                            bottomText = context.getString(R.string.weather_sunrise)
                            HourlyWeatherIcon(resource = R.drawable.sunrise_icon)
                        }

                        else -> {
                            bottomText = item.tempC
                            HourlyWeatherIcon(resource = item.conditionUrl)
                        }
                    }

                    Text(
                        text = bottomText,
                        color = textColor
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HourlyWeatherIcon(modifier: Modifier = Modifier, resource: Any) {
    GlideImage(
        modifier = modifier.size(40.dp),
        model = resource,
        contentDescription = null
    )
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

