package com.bignerdranch.android.weatherapp.presentation.favourite

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.bignerdranch.android.weatherapp.domain.entity.City
import com.bignerdranch.android.weatherapp.domain.entity.CurrentWeather
import com.bignerdranch.android.weatherapp.domain.usecase.ChangeFavouriteStateUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.GetFavoriteCitiesUseCase
import com.bignerdranch.android.weatherapp.presentation.favourite.FavouriteStore.Intent
import com.bignerdranch.android.weatherapp.presentation.favourite.FavouriteStore.Label
import com.bignerdranch.android.weatherapp.presentation.favourite.FavouriteStore.State
import com.bignerdranch.android.weatherapp.presentation.favourite.FavouriteStore.State.CityItem
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FavouriteStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data object ClickSearch : Intent

        data class RemoveFromFavourite(val cityId: Int) : Intent

        data object SaveRemovedCache : Intent

        data class GetLocationWeather(val cityName: String) : Intent

        data class CityItemClicked(val city: City) : Intent
    }

    data class State(val cityItems: FavouriteWeatherState) {

        data class FavouriteWeatherState(
            val locationWeather: LocationWeatherState,
            val cityItems: List<CityItem>
        )

        data class CityItem(
            val city: City,
            val weatherState: WeatherState
        )

        sealed interface WeatherState {

            data object Initial : WeatherState
            data object Loading : WeatherState
            data object Error : WeatherState
            data class Loaded(val currentWeather: CurrentWeather) : WeatherState
        }
    }

    sealed interface Label {

        data object ClickSearch : Label

        data class CityItemClicked(val city: City) : Label
    }
}

class FavouriteStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val removeFromFavouriteStateUseCase: ChangeFavouriteStateUseCase
) {

    fun create(): FavouriteStore =
        object : FavouriteStore, Store<Intent, State, Label> by storeFactory.create(
            name = "FavouriteStore",
            initialState = State(
                cityItems = State.FavouriteWeatherState(
                    locationWeather = LocationWeatherState.Initial,
                    cityItems = listOf()
                )
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {

        data class FavouriteCitiesLoaded(val cities: List<City>) : Action
    }

    private sealed interface Msg {

        data class CityRemoved(val cityItems: List<CityItem>) : Msg

        data class FavouriteCitiesLoaded(val cities: List<City>) : Msg

        data class LocationWeatherIsLoaded(val locationWeather: LocationWeatherState) : Msg

        data class LocationWeatherIsError(val locationWeather: LocationWeatherState) : Msg

        data class WeatherLoaded(
            val cityId: Int,
            val currentWeather: CurrentWeather
        ) : Msg

        data class WeatherLoadingError(val cityId: Int) : Msg

        data class WeatherIsLoading(val cityId: Int) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {

            scope.launch {
                getFavoriteCitiesUseCase().collect { cityList ->
                    dispatch(
                        Action.FavouriteCitiesLoaded(cityList)
                    )
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        private val deleteItemsCache = mutableListOf<suspend () -> Unit>()

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.CityItemClicked -> {
                    scope.launch {
                        try {
                            deleteItemsCache.forEach { it.invoke() }
                            deleteItemsCache.clear()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    publish(Label.CityItemClicked(intent.city))
                }

                Intent.ClickSearch -> {
                    scope.launch {
                        try {
                            deleteItemsCache.forEach { it.invoke() }
                            deleteItemsCache.clear()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    publish(Label.ClickSearch)
                }

                is Intent.RemoveFromFavourite -> {
                    val newState = getState().cityItems.cityItems.toMutableList()
                    val elementToRemove = newState.find { it.city.id == intent.cityId }
                    if (elementToRemove != null) {
                        newState.remove(elementToRemove)

                        deleteItemsCache.add {
                            try {
                                removeFromFavouriteStateUseCase.removeFromFavourite(intent.cityId)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        dispatch(
                            Msg.CityRemoved(newState)
                        )
                    }

                }

                is Intent.GetLocationWeather -> {
                    scope.launch {
                        loadWeatherForLocation(intent.cityName)
                    }
                }

                Intent.SaveRemovedCache -> {
                    scope.launch {
                        try {
                            deleteItemsCache.forEach { it.invoke() }
                            deleteItemsCache.clear()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FavouriteCitiesLoaded -> {
                    val cities = action.cities
                    dispatch(
                        Msg.FavouriteCitiesLoaded(cities)
                    )
                    cities.forEach {
                        scope.launch { loadWeatherForCity(it) }
                    }
                }
            }
        }

        private suspend fun loadWeatherForLocation(cityName: String) {

            try {
                val weather = getCurrentWeatherUseCase(cityName)
                dispatch(
                    Msg.LocationWeatherIsLoaded(
                        LocationWeatherState.Success(weather)
                    )
                )
            } catch (e: Exception) {
                dispatch(Msg.LocationWeatherIsError(LocationWeatherState.Error))
            }
        }

        private suspend fun loadWeatherForCity(city: City) {
            dispatch(Msg.WeatherIsLoading(city.id))
            try {
                val weather = getCurrentWeatherUseCase(city.name)
                dispatch(
                    Msg.WeatherLoaded(
                        cityId = city.id,
                        currentWeather = weather
                    )
                )

            } catch (e: Exception) {
                dispatch(Msg.WeatherLoadingError(city.id))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.FavouriteCitiesLoaded -> {
                copy(
                    cityItems = cityItems.copy(
                        cityItems = msg.cities.map {
                            CityItem(city = it, weatherState = State.WeatherState.Initial)
                        }
                    )
                )
            }

            is Msg.WeatherIsLoading -> {
                copy(
                    cityItems = cityItems.copy(
                        cityItems = cityItems.cityItems.map {
                            if (it.city.id == msg.cityId)
                                it.copy(weatherState = State.WeatherState.Loading)
                            else
                                it
                        }
                    )
                )
            }

            is Msg.WeatherLoaded -> {
                copy(
                    cityItems = cityItems.copy(
                        cityItems = cityItems.cityItems.map {
                            if (it.city.id == msg.cityId) {
                                it.copy(
                                    weatherState = State.WeatherState.Loaded(
                                        currentWeather = msg.currentWeather
                                    )
                                )
                            } else {
                                it
                            }
                        }
                    )


                )
            }

            is Msg.WeatherLoadingError -> {
                copy(
                    cityItems = cityItems.copy(
                        cityItems = cityItems.cityItems.map {
                            if (it.city.id == msg.cityId) {
                                it.copy(weatherState = State.WeatherState.Error)
                            } else {
                                it
                            }
                        }
                    )

                )
            }

            is Msg.CityRemoved -> {
                copy(
                    cityItems = cityItems.copy(
                        cityItems = msg.cityItems
                    )
                )
            }

            is Msg.LocationWeatherIsError -> {
                copy(
                    cityItems = cityItems.copy(locationWeather = msg.locationWeather)
                )
            }

            is Msg.LocationWeatherIsLoaded -> {
                copy(
                    cityItems = cityItems.copy(locationWeather = msg.locationWeather)
                )
            }
        }
    }
}
