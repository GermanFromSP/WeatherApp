package com.bignerdranch.android.weatherapp.presentation.favourite.elements

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.domain.entity.City
import com.bignerdranch.android.weatherapp.presentation.common.elements.CityNameAndWeatherCondition
import com.bignerdranch.android.weatherapp.presentation.common.elements.WeatherBackground
import com.bignerdranch.android.weatherapp.presentation.common.elements.WeatherTempAndIcon
import com.bignerdranch.android.weatherapp.presentation.common.extensions.tempToFormattedString
import com.bignerdranch.android.weatherapp.presentation.favourite.LocationWeatherState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GeolocationCityCard(
    modifier: Modifier = Modifier,
    city: City,
    locationWeatherState: LocationWeatherState,
    onCityClick: () -> Unit,
    getLocationWeather: (String) -> Unit,
    height: Dp = 120.dp,
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
            ),
        shape = ShapeDefaults.Large

    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            when (locationWeatherState) {
                LocationWeatherState.Error -> {

                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)

                    ) {
                        CityNameAndWeatherCondition(
                            city = city,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp),
                            weatherConditionText = stringResource(id = R.string.no_internet),
                            titleFontSize = titleFontSize,
                            conditionFontSize = conditionFontSize
                        )
                    }

                }

                is LocationWeatherState.Success -> {

                    val currentWeather = locationWeatherState.weather

                    WeatherBackground(
                        weatherConditionText = currentWeather.condition,
                        isDay = currentWeather.isDay
                    )

                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)

                    ) {
                        CityNameAndWeatherCondition(
                            city = city,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp),
                            weatherConditionText = currentWeather.condition,
                            locationName = stringResource(R.string.current_location),
                            titleFontSize = titleFontSize,
                            conditionFontSize = conditionFontSize
                        )
                        WeatherTempAndIcon(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp),
                            weatherConditionUrl = currentWeather.conditionUrl,
                            weatherTemp = currentWeather.tempC.tempToFormattedString(),
                            tempFontSize = tempFontSize,
                            iconSize = conditionIconSize
                        )
                    }
                }

                LocationWeatherState.Initial -> {
                    getLocationWeather(city.name)
                }

                LocationWeatherState.Loading -> {

                }
            }
        }
    }
}


@Composable
fun SearchCard(
    onClick: () -> Unit
) {

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onClick() }
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.3f)),

            ) {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Text(
                text = stringResource(R.string.button_search),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}