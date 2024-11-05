package com.bignerdranch.android.weatherapp.presentation.favourite.elements

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.domain.entity.City
import com.bignerdranch.android.weatherapp.domain.entity.CurrentWeather
import com.bignerdranch.android.weatherapp.presentation.common.elements.CityNameAndWeatherCondition
import com.bignerdranch.android.weatherapp.presentation.common.elements.WeatherBackground
import com.bignerdranch.android.weatherapp.presentation.common.elements.WeatherTempAndIcon
import com.bignerdranch.android.weatherapp.presentation.common.extensions.tempToFormattedString
import com.bignerdranch.android.weatherapp.presentation.favourite.FavouriteStore

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouriteCityCard(
    modifier: Modifier = Modifier,
    item: FavouriteStore.State.CityItem,
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

        Box(modifier = Modifier.fillMaxSize()) {
            when (item.weatherState) {
                FavouriteStore.State.WeatherState.Error -> {
                    FavouriteStateError(city = item.city, titleFontSize = titleFontSize, conditionFontSize = conditionFontSize)

                }

                FavouriteStore.State.WeatherState.Initial -> {

                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is FavouriteStore.State.WeatherState.Loaded -> {

                    val currentWeather = item.weatherState.currentWeather

                    FavouriteStateLoaded(
                        currentWeather = currentWeather,
                        city = item.city,
                        titleFontSize = titleFontSize,
                        conditionFontSize = conditionFontSize,
                        tempFontSize = tempFontSize,
                        conditionIconSize = conditionIconSize
                    )
                }

                FavouriteStore.State.WeatherState.Loading -> {
                    FavouriteStateLoading(titleFontSize = titleFontSize, city = item.city)
                }
            }
        }
    }
}

@Composable
private fun FavouriteStateError(
    city: City,
    titleFontSize: TextUnit,
    conditionFontSize: TextUnit
) {

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

@Composable
private fun FavouriteStateLoaded(
    currentWeather: CurrentWeather,
    city: City,
    titleFontSize: TextUnit,
    conditionFontSize: TextUnit,
    tempFontSize: TextUnit,
    conditionIconSize: Dp

) {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FavouriteStateLoading(
    titleFontSize: TextUnit,
    city: City
) {

    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)

    ) {
        Column(modifier = Modifier.padding(start = 8.dp).weight(1f)) {
            Text(
                text = city.name,
                fontSize = titleFontSize,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier.basicMarquee()
            )
            Text(
                text = city.country,
                fontSize = 12.sp,
                color = Color.White,
                maxLines = 1,
                modifier = Modifier.basicMarquee()
            )

            Spacer(modifier = Modifier.weight(1f))
        }
        Box(modifier = Modifier.weight(1f).fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
            )
        }
    }
}
