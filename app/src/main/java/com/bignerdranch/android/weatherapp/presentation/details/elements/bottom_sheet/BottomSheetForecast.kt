package com.bignerdranch.android.weatherapp.presentation.details.elements.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.domain.entity.ForecastWeather
import com.bignerdranch.android.weatherapp.presentation.common.extensions.formattedFullDay

@Composable
fun BottomSheetForecast(
    modifier: Modifier = Modifier,
    forecastWeather: ForecastWeather
) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = forecastWeather.date.formattedFullDay(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        BlockTitle(
            icon = Icons.Default.DeviceThermostat,
            text = stringResource(id = R.string.temp_indicators)
        )
        TemperatureBlock(
            forecastWeather = forecastWeather,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )

        BlockTitle(
            icon = Icons.Default.AccessTime,
            text = stringResource(id = R.string.twenty_four_hour_forecast_title)
        )

        HourlyForecastBlock(
            hourlyForecast = forecastWeather.hourlyForecast,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )

        BlockTitle(
            icon = Icons.Default.WaterDrop,
            text = stringResource(id = R.string.expected_precipitation)
        )

        PrecipitationBlock(
            chanceOfRain = forecastWeather.chanceOfRain,
            chanceOfSnow = forecastWeather.chanceOfSnow,
            humidity = forecastWeather.avgHumidity,
            modifier = Modifier.padding(16.dp)
        )

        BlockTitle(
            icon = Icons.Default.Air,
            text = stringResource(id = R.string.avg_wind_and_visibility)
        )

        WindAndVisibilityBlock(
            avgWindSpeed = forecastWeather.maxWindKph,
            visibility = forecastWeather.avgVisibility,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )
    }
}

