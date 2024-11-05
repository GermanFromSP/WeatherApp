package com.bignerdranch.android.weatherapp.presentation.details.elements.bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weatherapp.R

@Composable
fun PrecipitationBlock(
    modifier: Modifier = Modifier,
    chanceOfRain: Int,
    chanceOfSnow: Int,
    humidity: Int
) {
    val textColor = MaterialTheme.colorScheme.onSurface

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column {
            Row {
                WeatherIndicators(
                    name = stringResource(id = R.string.chance_of_rain),
                    indicator = "$chanceOfRain%"
                )
            }

            Row {
                WeatherIndicators(
                    name = stringResource(id = R.string.chance_of_snow),
                    indicator = "$chanceOfSnow%"
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "$humidity%", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textColor)
            Text(text = stringResource(id = R.string.avg_humidity), fontSize = 12.sp, color = textColor)
        }
    }
}