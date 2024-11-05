package com.bignerdranch.android.weatherapp.presentation.favourite

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.domain.entity.City
import com.bignerdranch.android.weatherapp.presentation.MainActivity.Companion.CITY_KEY
import com.bignerdranch.android.weatherapp.presentation.MainActivity.Companion.LAST_LOCATION
import com.bignerdranch.android.weatherapp.presentation.common.SharedPreferencesService
import com.bignerdranch.android.weatherapp.presentation.common.UserStore
import com.bignerdranch.android.weatherapp.presentation.favourite.elements.ListView
import com.bignerdranch.android.weatherapp.presentation.favourite.elements.SearchCard
import com.bignerdranch.android.weatherapp.presentation.favourite.elements.TableView
import com.bignerdranch.android.weatherapp.presentation.favourite.elements.TopBarFavouriteScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteContent(component: FavouriteComponent) {

    val state by component.model.collectAsState()
    val context = LocalContext.current
    val store = UserStore(context)
    val viewState by store.viewStatusFlow.collectAsState(initial = FavouriteCitiesListViewState.INITIAL)

    var deleteItemsState by rememberSaveable { mutableStateOf(false) }

    var deleteOneItemState by rememberSaveable { mutableStateOf(false) }

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    val systemUiController = rememberSystemUiController()
    val isDarkTheme = isSystemInDarkTheme()
    val statusBarColor = MaterialTheme.colorScheme.background

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = !isDarkTheme
        )
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP || event == Lifecycle.Event.ON_DESTROY) {
                component.saveRemovedElementsCache()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val sharedPreferences = SharedPreferencesService(LocalContext.current)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {

            Column {
                TopBarFavouriteScreen(
                    scrollBehavior = scrollBehavior,
                    title = stringResource(R.string.weather),
                    onTableClick = {
                        coroutineScope.launch {
                            store.setViewStatus(FavouriteCitiesListViewState.TABLE)
                        }
                    },
                    onListClick = {
                        coroutineScope.launch {
                            store.setViewStatus(FavouriteCitiesListViewState.LIST)
                        }
                    },
                    onDeleteClick = { deleteItemsState = !deleteItemsState },
                    onDoneClick = {
                        deleteItemsState = false
                        deleteOneItemState = false
                    },
                    deleteState = deleteItemsState || deleteOneItemState,
                    canDelete = state.cityItems.cityItems.isNotEmpty()
                )
                SearchCard(onClick = component::onClickSearch)
            }

        },
        modifier = Modifier
            .statusBarsPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.statusBars)

    ) { padding ->


        AnimatedContent(
            targetState = viewState,
            transitionSpec = {
                fadeIn(tween(500)) togetherWith fadeOut(tween(200))
            },
            label = ""
        ) { viewState ->

            when (viewState) {
                FavouriteCitiesListViewState.LIST -> {
                    ListView(
                        padding = padding,
                        items = state,
                        onCityItemClick = {
                            if (deleteItemsState) deleteItemsState = false
                            component.onCityItemClick(it)
                        },
                        deleteItemsState = deleteItemsState,
                        onDeleteClick = { cityId ->
                            val deletedCity =
                                state.cityItems.cityItems.find { it.city.id == cityId }?.city
                            checkCityToBeDeleted(sharedPreferences, deletedCity)
                            component.onClickRemoveFromFavourite(cityId)
                        },
                        onLongClick = { deleteOneItemState = it },
                        deleteOneItemState = deleteOneItemState,
                        getLocationWeather = component::getLocationWeather
                    )
                }

                FavouriteCitiesListViewState.TABLE -> {
                    TableView(
                        padding = padding,
                        items = state,
                        onCityItemClick = component::onCityItemClick,
                        onDeleteClick = { cityId ->
                            val deletedCity =
                                state.cityItems.cityItems.find { it.city.id == cityId }?.city
                            checkCityToBeDeleted(sharedPreferences, deletedCity)
                            component.onClickRemoveFromFavourite(cityId)
                        },
                        deleteItemsState = deleteItemsState,
                        deleteOneItemState = deleteOneItemState,
                        onLongClick = { deleteOneItemState = it },
                        getLocationWeather = component::getLocationWeather
                    )
                }

                FavouriteCitiesListViewState.INITIAL -> {}
            }
        }
    }
}

private fun checkCityToBeDeleted(
    sharedPreferences: SharedPreferencesService,
    deletedCity: City?
) {
    val cityForCheck = sharedPreferences.getData(CITY_KEY, null)
    if (deletedCity?.name == cityForCheck?.name) {
        val lastLocation = sharedPreferences.getData(LAST_LOCATION, null)
        sharedPreferences.saveData(CITY_KEY, lastLocation)
    }
}



