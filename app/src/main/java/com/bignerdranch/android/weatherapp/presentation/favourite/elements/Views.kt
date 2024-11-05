package com.bignerdranch.android.weatherapp.presentation.favourite.elements

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weatherapp.WeatherApp
import com.bignerdranch.android.weatherapp.domain.entity.City
import com.bignerdranch.android.weatherapp.presentation.MainActivity.Companion.LAST_LOCATION
import com.bignerdranch.android.weatherapp.presentation.common.SharedPreferencesService
import com.bignerdranch.android.weatherapp.presentation.common.getCityFromGeo
import com.bignerdranch.android.weatherapp.presentation.favourite.FavouriteStore

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListView(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    items: FavouriteStore.State,
    onCityItemClick: (City) -> Unit,
    deleteItemsState: Boolean,
    deleteOneItemState: Boolean,
    onDeleteClick: (Int) -> Unit,
    onLongClick: (Boolean) -> Unit,
    getLocationWeather: (String) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val deleteItemIndexState = remember {
        mutableStateListOf<Int>()
    }

    val currentLocation =
        (LocalContext.current.applicationContext as WeatherApp).location.collectAsState()

    if (!deleteOneItemState) deleteItemIndexState.clear()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(padding)
            .animateContentSize()

    ) {
        item {

            val latitude = currentLocation.value.latitude
            val longitude = currentLocation.value.longitude
            val sharedPreferences = SharedPreferencesService(LocalContext.current)

            val city = getCityFromGeo(latitude, longitude, LocalContext.current)

            sharedPreferences.saveData(LAST_LOCATION, city)

            GeolocationCityCard(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
                height = 100.dp,
                city = city,
                locationWeatherState = items.cityItems.locationWeather,
                onCityClick = { onCityItemClick(city) },
                getLocationWeather = { getLocationWeather(it) }
            )

        }

        itemsIndexed(
            items = items.cityItems.cityItems,
            key = { _, item -> item.city.id }) { index, city ->

            Row(
                modifier = Modifier.animateItemPlacement()
            ) {
                AnimatedContent(
                    targetState = deleteItemsState || deleteItemIndexState.contains(index),
                    label = ""
                ) {
                    if (it) {
                        Box(
                            modifier = Modifier
                                .height(120.dp)
                                .width(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            DeleteIcon(
                                size = 36.dp,
                                onIconClick = {
                                    onDeleteClick(city.city.id)

                                }
                            )
                        }
                    }
                }

                FavouriteCityCard(
                    item = city,
                    onCityClick = { onCityItemClick(city.city) },
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
                    height = 100.dp,
                    onLongClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        if (!deleteItemsState) {
                            onLongClick(true)
                            deleteItemIndexState.add(index)
                        }
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TableView(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    items: FavouriteStore.State,
    onCityItemClick: (City) -> Unit,
    onDeleteClick: (Int) -> Unit,
    deleteItemsState: Boolean,
    deleteOneItemState: Boolean,
    onLongClick: (Boolean) -> Unit,
    getLocationWeather: (String) -> Unit
) {

    val haptic = LocalHapticFeedback.current

    val deleteItemIndexState = remember {
        mutableStateListOf<Int>()
    }

    val currentLocation =
        (LocalContext.current.applicationContext as WeatherApp).location.collectAsState()

    if (!deleteOneItemState) deleteItemIndexState.clear()

    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp)
                .animateContentSize()
        ) {

            item {

                val latitude = currentLocation.value.latitude
                val longitude = currentLocation.value.longitude

                val city = getCityFromGeo(latitude, longitude, LocalContext.current)

                GeolocationCityCard(
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp),
                    city = city,
                    locationWeatherState = items.cityItems.locationWeather,
                    onCityClick = { onCityItemClick(city) },
                    getLocationWeather = { getLocationWeather(it) },
                    conditionFontSize = 12.sp,
                    titleFontSize = 18.sp,
                    tempFontSize = 24.sp,
                    conditionIconSize = 28.dp,
                )
            }

            itemsIndexed(
                items = items.cityItems.cityItems,
                key = { _, item -> item.city.id }) { index, city ->

                Column(
                    modifier = Modifier
                        .height(120.dp)
                        .padding(if (deleteItemsState) 8.dp else 0.dp)
                        .animateItemPlacement()
                ) {
                    AnimatedContent(
                        targetState = deleteItemsState || deleteItemIndexState.contains(index),
                        label = ""
                    ) { deleteState ->
                        if (deleteState) {
                            DeleteIcon(
                                size = 24.dp,
                                onIconClick = {
                                    onDeleteClick(city.city.id)
                                    if (!deleteItemsState) onLongClick(false)
                                }
                            )
                        }
                    }

                    FavouriteCityCard(
                        item = city,
                        onCityClick = { onCityItemClick(city.city) },
                        modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp),
                        height = if (deleteItemsState) 100.dp else 120.dp,
                        onLongClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            if (index > 0 && !deleteItemsState) {
                                onLongClick(true)
                                deleteItemIndexState.add(index)
                            }
                        },
                        conditionFontSize = 12.sp,
                        titleFontSize = 18.sp,
                        tempFontSize = 24.sp,
                        conditionIconSize = 28.dp,
                    )
                }
            }
        }
    }
}