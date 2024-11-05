package com.bignerdranch.android.weatherapp.presentation.details.elements.bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.domain.entity.WeatherInHour
import com.bignerdranch.android.weatherapp.presentation.details.elements.HourlyWeatherIcon

@Composable
fun HourlyForecastBlock(
    modifier: Modifier = Modifier,
    hourlyForecast: List<WeatherInHour>
) {
    val textColor = MaterialTheme.colorScheme.onSurface
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(8.dp)
    ) {


        itemsIndexed(hourlyForecast) { index, item ->

            val context = LocalContext.current

            Column(
                modifier = modifier.padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val bottomText: String

                Text(text = item.time, color = textColor)

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

                Text(text = bottomText, color = textColor)
            }
        }
    }

}