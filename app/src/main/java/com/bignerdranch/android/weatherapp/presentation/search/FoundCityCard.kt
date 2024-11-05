package com.bignerdranch.android.weatherapp.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weatherapp.presentation.common.elements.CityNameAndWeatherCondition
import com.bignerdranch.android.weatherapp.presentation.common.elements.WeatherBackground
import com.bignerdranch.android.weatherapp.presentation.common.elements.WeatherTempAndIcon
import com.bignerdranch.android.weatherapp.presentation.common.extensions.tempToFormattedString

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoundCityCard(
    modifier: Modifier = Modifier,
    foundCityItem: FoundCityItem,
    onCityClick: () -> Unit,
    onLongClick: () -> Unit,
    height: Dp = 100.dp,
    titleFontSize: TextUnit = 21.sp,
    conditionFontSize: TextUnit = 14.sp,
    tempFontSize: TextUnit = 32.sp,
    conditionIconSize: Dp = 36.dp
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
            .height(height)
            .combinedClickable(
                onClick = { onCityClick() },
                onLongClick = { onLongClick() }
            ),
        shape = ShapeDefaults.Large
    ) {


        val weather = foundCityItem.currentWeather
        val weatherConditionText = weather.condition
        val weatherTemp = weather.tempC.tempToFormattedString()
        val weatherConditionUrl = weather.conditionUrl
        val isDay = weather.isDay


        Box(modifier = Modifier.fillMaxSize()) {

            WeatherBackground(weatherConditionText = weatherConditionText, isDay = isDay)

            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)

            ) {
                CityNameAndWeatherCondition(
                    city = foundCityItem.city,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    weatherConditionText = weatherConditionText,
                    titleFontSize = titleFontSize,
                    conditionFontSize = conditionFontSize
                )
                WeatherTempAndIcon(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    weatherConditionUrl = weatherConditionUrl,
                    weatherTemp = weatherTemp,
                    tempFontSize = tempFontSize,
                    iconSize = conditionIconSize
                )
            }
        }
    }
}







