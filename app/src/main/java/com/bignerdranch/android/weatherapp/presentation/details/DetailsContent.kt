package com.bignerdranch.android.weatherapp.presentation.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.domain.entity.City
import com.bignerdranch.android.weatherapp.domain.entity.Forecast
import com.bignerdranch.android.weatherapp.presentation.MainActivity.Companion.CITY_KEY
import com.bignerdranch.android.weatherapp.presentation.common.SharedPreferencesService
import com.bignerdranch.android.weatherapp.presentation.common.elements.WeatherBackground
import com.bignerdranch.android.weatherapp.presentation.common.extensions.tempToFormattedString
import com.bignerdranch.android.weatherapp.presentation.details.elements.AnimatedHourlyWeather
import com.bignerdranch.android.weatherapp.presentation.details.elements.AnimatedUpcomingWeather
import com.bignerdranch.android.weatherapp.presentation.details.elements.bottom_sheet.BottomSheetForecast
import com.bignerdranch.android.weatherapp.presentation.details.elements.HumidityContent
import com.bignerdranch.android.weatherapp.presentation.details.elements.Loading
import com.bignerdranch.android.weatherapp.presentation.details.elements.PrecipitationContent
import com.bignerdranch.android.weatherapp.presentation.details.elements.PressureContent
import com.bignerdranch.android.weatherapp.presentation.details.elements.VisibilityContent
import com.bignerdranch.android.weatherapp.presentation.details.elements.WindInfo
import com.bignerdranch.android.weatherapp.presentation.search.OpenReason
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsContent(component: DetailsComponent) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val sheetState = rememberModalBottomSheetState()

    var showBottomSheet by remember { mutableStateOf(false) }

    val isDarkTheme = isSystemInDarkTheme()
    val errorSystemBarColor = MaterialTheme.colorScheme.background
    val systemUiController = rememberSystemUiController()
    val state by component.model.collectAsState()

    when (val forecastState = state.forecastState) {
        DetailStore.State.ForecastState.Error -> {

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = errorSystemBarColor,
                    darkIcons = !isDarkTheme
                )
            }

            Error(
                topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                onBackClick = { component.onClickBack() },
                cityName = state.city.name,
                onClickChangeFavouriteStatus = { /*TODO*/ },
                openReason = OpenReason.SeeDetails,
                onUpdateClick = component::onClickUpdate,
                isCurrentLocation = state.city.isCurrentLocation,
                isCityFavourite = true
            )
        }

        DetailStore.State.ForecastState.Initial -> {
            Initial()
        }

        is DetailStore.State.ForecastState.Loaded -> {

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = Color.Black.copy(alpha = 0.3f),
                    darkIcons = !isDarkTheme
                )
            }

            Box(modifier = Modifier.fillMaxSize()) {

                val sharedPreferences = SharedPreferencesService(LocalContext.current)
                if (state.isFavourite || state.city.isCurrentLocation)
                    sharedPreferences.saveData(CITY_KEY, state.city)


                val currentWeather = forecastState.forecast.currentWeather

                WeatherBackground(
                    isDay = currentWeather.isDay,
                    weatherConditionText = currentWeather.condition
                )

                WeatherContent(
                    forecast = forecastState.forecast,
                    city = state.city,
                    state = state,
                    onBackClick = component::onClickBack,
                    topAppBarScrollBehavior = scrollBehavior,
                    sheetState = sheetState,
                    changeBottomSheetVisible = { showBottomSheet = !showBottomSheet },
                    showBottomSheet = showBottomSheet,
                    onClickChangeFavouriteStatus = component::onClickChangeFavouriteStatus,
                )
            }
        }

        DetailStore.State.ForecastState.Loading -> {
            Loading()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherContent(
    modifier: Modifier = Modifier,
    forecast: Forecast,
    city: City,
    state: DetailStore.State,
    onBackClick: () -> Unit,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    sheetState: SheetState,
    showBottomSheet: Boolean,
    onClickChangeFavouriteStatus: () -> Unit,
    changeBottomSheetVisible: (Boolean) -> Unit
) {

    var forecastWeatherDayIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                changeBottomSheetVisible(false)
            },
            sheetState = sheetState,
            windowInsets = BottomSheetDefaults.windowInsets.only(WindowInsetsSides.End),
            modifier = modifier,
        ) {
            BottomSheetForecast(
                forecastWeather = forecast.upcoming[forecastWeatherDayIndex],
                modifier = Modifier.padding(16.dp)
            )
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBarDetailsScreen(
                isCityFavourite = state.isFavourite,
                onBackClick = { onBackClick() },
                title = city.name,
                scrollBehavior = topAppBarScrollBehavior,
                openReason = state.openReason,
                isCurrentLocation = city.isCurrentLocation,
                onChangeFavouriteStateClick = {onClickChangeFavouriteStatus()}
            )
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.statusBars),
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),

        ) { paddingValues ->

        Forecast(
            forecast = forecast,
            city = city,
            modifier = Modifier.padding(paddingValues),
            onForecastDayClick = { forecastWeatherDayIndex = it },
            showBottomSheet = { changeBottomSheetVisible(true) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Error(
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onBackClick: () -> Unit,
    cityName: String,
    onClickChangeFavouriteStatus: () -> Unit,
    openReason: OpenReason,
    isCurrentLocation: Boolean ,
    isCityFavourite: Boolean,
    onUpdateClick: () -> Unit
) {

    val textColor = MaterialTheme.colorScheme.onBackground

    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBarDetailsScreen(
                isCityFavourite = isCityFavourite,
                onBackClick = { onBackClick() },
                onChangeFavouriteStateClick = { onClickChangeFavouriteStatus() },
                title = cityName,
                scrollBehavior = topAppBarScrollBehavior,
                openReason = openReason,
                isCurrentLocation = isCurrentLocation,
                stateElementColor = textColor
            )
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.statusBars),
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),

        ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.no_internet_connection),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                colorFilter = ColorFilter.tint(textColor)
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp))

            Text(text = stringResource(id = R.string.no_internet), color = textColor)

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp))

            Button(onClick = { onUpdateClick() }) {
                Text(text = stringResource(id = R.string.update))

            }
        }
    }
}

@Composable
private fun Initial() {
    Loading()
}

@Composable
private fun Forecast(
    forecast: Forecast,
    city: City,
    modifier: Modifier,
    onForecastDayClick: (Int) -> Unit,
    showBottomSheet: () -> Unit
) {

    val currentWeather = forecast.currentWeather

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        MainInfo(cityName = city.name, forecast = forecast)
        AnimatedHourlyWeather(weather = forecast.currentHourlyForecast)
        AnimatedUpcomingWeather(
            upcoming = forecast.upcoming,
            onDayClick = { onForecastDayClick(it) },
            showBottomSheet = { showBottomSheet() }
        )
        HumidityAndPressureInfo(
            humidity = currentWeather.humidity,
            dewPoint = currentWeather.dewPoint,
            pressureMmM = currentWeather.pressureMmM
        )
        WindInfo(currentWeather = currentWeather)
        VisibilityAndPrecipitationInfo(
            visibilityKm = currentWeather.visibilityKm,
            precipitationsMM = currentWeather.precipitationsMm
        )
    }
}

@Composable
fun HumidityAndPressureInfo(
    humidity: Int,
    dewPoint: Float,
    pressureMmM: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val contentModifier = Modifier
            .height(160.dp)
            .weight(1f)

        HumidityContent(humidity = humidity, dewPoint = dewPoint, modifier = contentModifier)
        PressureContent(pressureMmM = pressureMmM, modifier = contentModifier)
    }
}

@Composable
fun VisibilityAndPrecipitationInfo(
    modifier: Modifier = Modifier,
    visibilityKm: Int,
    precipitationsMM: Int
) {
    Row(
        modifier = modifier.padding(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 8.dp)
    ) {
        val contentModifier = Modifier
            .height(160.dp)
            .weight(1f)

        VisibilityContent(visibilityKm = visibilityKm, modifier = contentModifier)
        PrecipitationContent(precipitationMm = precipitationsMM, modifier = contentModifier)
    }
}

@Composable
private fun MainInfo(modifier: Modifier = Modifier, cityName: String, forecast: Forecast) {

    val textColor = MaterialTheme.colorScheme.onBackground

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = forecast.currentWeather.condition,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            color = textColor
        )

        Text(
            text = forecast.currentWeather.tempC.tempToFormattedString(),
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 70.sp),
            color = textColor
        )

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.min_temp) + forecast.upcoming.first().minTempC,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                modifier = modifier.padding(end = 16.dp),
                color = textColor
            )

            Text(
                text = stringResource(id = R.string.max_temp) + forecast.upcoming.first().maxTempC,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                modifier = modifier.padding(start = 16.dp),
                color = textColor
            )
        }

        Text(
            text = stringResource(R.string.feels_like) + forecast.currentWeather.feelsLikeC.tempToFormattedString(),
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 16.sp),
            color = textColor
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun TopBarDetailsScreen(
    isCityFavourite: Boolean,
    isCurrentLocation: Boolean,
    onBackClick: () -> Unit,
    onChangeFavouriteStateClick: () -> Unit,
    title: String,
    openReason: OpenReason,
    scrollBehavior: TopAppBarScrollBehavior,
    stateElementColor: Color = MaterialTheme.colorScheme.onBackground
) {

    val clickBack = Modifier.clickable { onBackClick() }

    LargeTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .basicMarquee()
            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            titleContentColor = stateElementColor,
            scrolledContainerColor = Color.Black.copy(alpha = 0.3f),
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            when (openReason) {
                OpenReason.SeeDetails -> {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = null,
                        tint = stateElementColor,
                        modifier = clickBack
                    )
                }

                OpenReason.RegularSearch -> {
                    Text(
                        text = stringResource(R.string.cancel),
                        modifier = clickBack.padding(8.dp),
                        color = stateElementColor
                    )
                }
            }
        },
        actions = {
            if (isCityFavourite || isCurrentLocation) {
                Text(text = "")
            } else {
                Text(
                    text = stringResource(R.string.add_to_favourite),
                    modifier = Modifier
                        .clickable {
                            onChangeFavouriteStateClick()
                        }
                        .padding(8.dp),
                    color = stateElementColor
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

