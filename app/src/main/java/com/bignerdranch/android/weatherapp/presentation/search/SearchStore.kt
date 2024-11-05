package com.bignerdranch.android.weatherapp.presentation.search

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.bignerdranch.android.weatherapp.domain.entity.City
import com.bignerdranch.android.weatherapp.domain.usecase.ChangeFavouriteStateUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.SearchCityUseCase
import com.bignerdranch.android.weatherapp.presentation.search.OpenReason.*
import com.bignerdranch.android.weatherapp.presentation.search.SearchStore.Intent
import com.bignerdranch.android.weatherapp.presentation.search.SearchStore.Label
import com.bignerdranch.android.weatherapp.presentation.search.SearchStore.State
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SearchStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data class ChangeSearchQuery(val query: String) : Intent

        data object ClickBack : Intent

        data object ClickSearch : Intent

        data class ClickCity(val city: City) : Intent
    }


    data class State(
        val searchQuery: String,
        val searchState: SearchState
    ) {

        sealed interface SearchState {

            data object Initial : SearchState

            data object Loading : SearchState

            data object Error : SearchState

            data object EmptyResult : SearchState

            data class SuccessLoaded(val cities: List<FoundCityItem>) : SearchState
        }
    }

    sealed interface Label {

        data object ClickBack : Label

        data object SavedToFavourite : Label

        data class OpenForecast(val city: City) : Label
    }
}

class SearchStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val searchCityUseCase: SearchCityUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) {

    fun create(): SearchStore =
        object : SearchStore, Store<Intent, State, Label> by storeFactory.create(
            name = "SearchStore",
            initialState = State(
                searchQuery = "",
                searchState = State.SearchState.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl() },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg {

        data class ChangeSearchQuery(val query: String) : Msg

        data object LoadingSearchResult : Msg

        data object SearchError : Msg

        data class SearchResultLoaded(val cities: List<FoundCityItem>) : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private inner class ExecutorImpl(
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        private var searchJob: Job? = null

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ChangeSearchQuery -> {
                    searchJob?.cancel()
                    searchJob = scope.launch {
                        dispatch(Msg.ChangeSearchQuery(intent.query))
                        dispatch(Msg.LoadingSearchResult)
                        try {

                            val cities = searchCityUseCase.invoke(intent.query)
                            val foundedCities = cities.map { city ->
                                FoundCityItem(
                                    city = city,
                                    currentWeather = getCurrentWeatherUseCase.invoke(city.name)
                                )
                            }
                            dispatch(Msg.SearchResultLoaded(foundedCities))
                        } catch (e: Exception) {
                            dispatch(Msg.SearchError)
                        }
                    }
                }

                Intent.ClickBack -> publish(Label.ClickBack)

                is Intent.ClickCity -> publish(Label.OpenForecast(intent.city))
                Intent.ClickSearch -> {}
            }
        }

    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.ChangeSearchQuery -> {
                copy(searchQuery = msg.query)
            }

            Msg.LoadingSearchResult -> {
                copy(searchState = State.SearchState.Loading)
            }

            Msg.SearchError -> {

                copy(searchState = State.SearchState.Error)
            }

            is Msg.SearchResultLoaded -> {
                val searchState = if (msg.cities.isEmpty()) {
                    State.SearchState.EmptyResult
                } else {
                    State.SearchState.SuccessLoaded(msg.cities)
                }
                copy(searchState = searchState)
            }
        }
    }
}
