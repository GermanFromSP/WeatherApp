package com.bignerdranch.android.weatherapp.presentation.common.elements

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weatherapp.domain.entity.City
import com.bignerdranch.android.weatherapp.presentation.common.extensions.getDayWeatherImage
import com.bignerdranch.android.weatherapp.presentation.common.extensions.getNightWeatherConditionState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityNameAndWeatherCondition(
    modifier: Modifier = Modifier,
    city: City,
    locationName: String = "",
    weatherConditionText: String,
    titleFontSize: TextUnit,
    conditionFontSize: TextUnit
) {

    val textColor = Color.White

    Column(modifier = modifier) {
        Text(
            text = locationName.ifEmpty { city.name },
            fontSize = titleFontSize,
            color = textColor,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            modifier = Modifier.basicMarquee()
        )
        Text(
            text = city.country,
            fontSize = 12.sp,
            color = textColor,
            maxLines = 1,
            modifier = Modifier.basicMarquee()
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = weatherConditionText,
            fontSize = conditionFontSize,
            color = textColor,
            maxLines = 1,
            modifier = Modifier.basicMarquee()
        )
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WeatherTempAndIcon(
    modifier: Modifier = Modifier,
    weatherTemp: String,
    weatherConditionUrl: String,
    tempFontSize: TextUnit,
    iconSize: Dp
) {

    val textColor = Color.White

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center

    ) {

        Text(text = weatherTemp, fontSize = tempFontSize, color = textColor)
        GlideImage(
            modifier = Modifier.size(iconSize),
            model = weatherConditionUrl,
            contentDescription = null
        )

    }
}

@Composable
fun WeatherBackground(
    modifier: Modifier = Modifier,
    isDay: Boolean,
    weatherConditionText: String
) {

    val weatherImageResId = if (isDay) {
        getDayWeatherImage(
            conditionText = weatherConditionText,
            context = LocalContext.current
        )
    } else {
        getNightWeatherConditionState(
            conditionText = weatherConditionText,
            context = LocalContext.current
        )
    }

    Image(
        modifier = modifier.fillMaxSize(),
        painter = painterResource(id = weatherImageResId),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
    )
}
