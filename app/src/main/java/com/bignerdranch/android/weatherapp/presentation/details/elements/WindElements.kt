package com.bignerdranch.android.weatherapp.presentation.details.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.domain.entity.CurrentWeather
import com.bignerdranch.android.weatherapp.presentation.common.extensions.getWindDirectionImageResId
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WindInfo(modifier: Modifier = Modifier, currentWeather: CurrentWeather) {

    DetailsCard(
        titleIcon = Icons.Default.Air,
        titleText = stringResource(id = R.string.wind_card_title),
        modifier = Modifier .padding(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 8.dp)
    ) {
        val imageResId = getWindDirectionImageResId(currentWeather.windDirection)
        Row(
            horizontalArrangement = Arrangement.Center, modifier = modifier.fillMaxSize()
        ) {
            WindSpeedValues(
                windSpeed = currentWeather.windKph,
                windGust = currentWeather.windGustKph,
                modifier = modifier
                    .weight(1f)
                    .padding(top = 24.dp, end = 8.dp, start = 8.dp)
            )

            GlideImage(
                model = imageResId,
                contentDescription = null,
                modifier = modifier
                    .weight(0.7f)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun WindSpeedValues(
    modifier: Modifier = Modifier,
    windSpeed: String,
    windGust: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        SpeedValue(description = stringResource(id = R.string.wind), speed = windSpeed)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .height(1.dp)
                .background(Color.Gray)
        )
        SpeedValue(description = stringResource(id = R.string.wind_gust), speed = windGust)
    }
}

@Composable
fun SpeedValue(
    modifier: Modifier = Modifier,
    description: String,
    speed: String
) {

    val textColor = MaterialTheme.colorScheme.onBackground
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val weight = if (speed.length >= 2) 0.8f else 0.4f
        Text(
            text = speed,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(start = 16.dp, end = 8.dp)
                .weight(weight),
            color = textColor

        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.meters_per_second),
                fontSize = 12.sp,
                color = textColor,
                lineHeight = 13.sp
            )
            Text(text = description, fontSize = 14.sp, maxLines = 1, lineHeight = 15.sp,
                color = textColor)
        }
    }
}

