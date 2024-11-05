package com.bignerdranch.android.weatherapp.presentation.details.elements.bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.domain.entity.ForecastWeather
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TemperatureBlock(
    modifier: Modifier = Modifier,
    forecastWeather: ForecastWeather
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Temperature(
            avg = forecastWeather.avgTempC,
            max = forecastWeather.maxTempC,
            min = forecastWeather.minTempC,
            modifier = Modifier.padding(start = 8.dp)
        )



        Spacer(modifier = Modifier.weight(1f))

        GlideImage(
            model = forecastWeather.conditionImageUrl,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
private fun Temperature(
    modifier: Modifier = Modifier,
    avg: String,
    max: String,
    min: String
) {
    Column(
        modifier = modifier
    ) {
        WeatherIndicators(name = stringResource(id = R.string.avg_temp), indicator = avg)
        WeatherIndicators(name = stringResource(id = R.string.min_temp_full), indicator = min)
        WeatherIndicators(name = stringResource(id = R.string.max_temp_full), indicator = max)
    }
}

