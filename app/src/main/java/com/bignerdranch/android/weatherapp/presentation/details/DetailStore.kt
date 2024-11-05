package com.bignerdranch.android.weatherapp.presentation.details

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.bignerdranch.android.weatherapp.domain.entity.City
import com.bignerdranch.android.weatherapp.domain.entity.Forecast
import com.bignerdranch.android.weatherapp.domain.usecase.ChangeFavouriteStateUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.GetForecastUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.ObserveFavouriteStateUseCase
import com.bignerdranch.android.weatherapp.presentation.details.DetailStore.Intent
import com.bignerdranch.android.weatherapp.presentation.details.DetailStore.Label
import com.bignerdranch.android.weatherapp.presentation.details.DetailStore.State
import com.bignerdranch.android.weatherapp.presentation.search.OpenReason
import kotlinx.coroutines.launch
import javax.inject.Inject

interface DetailStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data object ClickBack : Intent

        data object ClickUpdate : Intent

        data object ClickChangeFavouriteStatus : Intent

    }

    data class State(
        val city: City,
        val isFavourite: Boolean,
        val forecastState: ForecastState,
        val openReason: OpenReason
    ) {

        sealed interface ForecastState {

            data object Initial : ForecastState

            data object Loading : ForecastState

            data object Error : ForecastState

            data class Loaded(val forecast: Forecast) : ForecastState
        }
    }

    sealed interface Label {

        data object ClickBack : Label
        data object NavigateToFavourite : Label
    }
}
 class DetailStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getForecastUseCase: GetForecastUseCase,
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCase,
    private val observeFavouriteStateUseCase: ObserveFavouriteStateUseCase
) {

    fun create(city: City, openReason: OpenReason): DetailStore =
        object : DetailStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DetailStore",
            initialState = State(
                city = city,
                isFavourite = false,
                forecastState = State.ForecastState.Initial,
                openReason = openReason
            ),
            bootstrapper = BootstrapperImpl(city),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {

        data class FavouriteStatusChange(val isFavourite: Boolean) : Action

        data class ForecastLoaded(val forecast: Forecast) : Action

        data object ForecastStartLoading : Action

        data object ForecastLoadingError : Action
    }

    private sealed interface Msg {

        data class FavouriteStatusChange(val isFavourite: Boolean) : Msg

        data class ForecastLoaded(val forecast: Forecast) : Msg

        data object ForecastStartLoading : Msg

        data object ForecastLoadingError : Msg
    }

    private inner class BootstrapperImpl(private val city: City) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                observeFavouriteStateUseCase(cityId = city.id).collect {
                    dispatch(Action.FavouriteStatusChange(it))
                }
            }

            scope.launch {
                dispatch(Action.ForecastStartLoading)
                try {
                    val forecast = getForecastUseCase(city.name, 4)
                    dispatch(Action.ForecastLoaded(forecast))
                } catch (e: Exception) {
                    dispatch(Action.ForecastLoadingError)
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }

                Intent.ClickUpdate -> {
                    scope.launch {
                        dispatch(Msg.ForecastStartLoading)
                        try {
                            val forecast = getForecastUseCase(getState.invoke().city.name, 4)
                            dispatch(Msg.ForecastLoaded(forecast))
                        } catch (e: Exception) {
                            dispatch(Msg.ForecastLoadingError)
                        }
                    }
                }

                Intent.ClickChangeFavouriteStatus -> {
                    scope.launch {
                        changeFavouriteStateUseCase.addToFavourite(getState.invoke().city)
                    }
                    publish(label = Label.NavigateToFavourite)
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when(action) {

                is Action.FavouriteStatusChange -> {
                    dispatch(Msg.FavouriteStatusChange(action.isFavourite))
                }
                is Action.ForecastLoaded -> {
                    dispatch(Msg.ForecastLoaded(action.forecast))
                }
                Action.ForecastLoadingError -> {
                    dispatch(Msg.ForecastLoadingError)
                }
                Action.ForecastStartLoading -> {
                    dispatch(Msg.ForecastStartLoading)
                }

            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when(msg) {

            is Msg.ForecastLoaded -> {
                copy(forecastState = State.ForecastState.Loaded(forecast = msg.forecast))
            }
            Msg.ForecastLoadingError -> {
                copy(forecastState = State.ForecastState.Error)
            }
            Msg.ForecastStartLoading -> {
                copy(forecastState = State.ForecastState.Loading)
            }

            is Msg.FavouriteStatusChange -> copy(isFavourite = msg.isFavourite)
        }
    }
}
