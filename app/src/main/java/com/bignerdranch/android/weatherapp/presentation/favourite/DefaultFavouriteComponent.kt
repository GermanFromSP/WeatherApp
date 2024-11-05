package com.bignerdranch.android.weatherapp.presentation.favourite

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.bignerdranch.android.weatherapp.domain.entity.City
import com.bignerdranch.android.weatherapp.presentation.common.extensions.componentScope
import com.bignerdranch.android.weatherapp.presentation.search.OpenReason
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultFavouriteComponent @AssistedInject constructor(
   private val favouriteStoreFactory: FavouriteStoreFactory,
   @Assisted("onCityItemClicked") private val onCityItemClicked: (City) -> Unit,
   @Assisted("onSearchClicked") private val onSearchClicked: (OpenReason) -> Unit,
   @Assisted("componentContext") componentContext: ComponentContext
) : FavouriteComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { favouriteStoreFactory.create() }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    is FavouriteStore.Label.CityItemClicked -> {
                        onCityItemClicked(it.city)
                    }

                    FavouriteStore.Label.ClickSearch -> {
                        onSearchClicked(OpenReason.RegularSearch)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<FavouriteStore.State> = store.stateFlow

    override fun onClickSearch() {
        store.accept(FavouriteStore.Intent.ClickSearch)
    }

    override fun getLocationWeather(cityName: String) {
        store.accept(FavouriteStore.Intent.GetLocationWeather(cityName))
    }

    override fun saveRemovedElementsCache() {
        store.accept(FavouriteStore.Intent.SaveRemovedCache)
    }

    override fun onClickRemoveFromFavourite(cityId: Int) {
        store.accept(FavouriteStore.Intent.RemoveFromFavourite(cityId))
    }


    override fun onCityItemClick(city: City) {
        store.accept(FavouriteStore.Intent.CityItemClicked(city))
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("onCityItemClicked") onCityItemClicked: (City) -> Unit,
            @Assisted("onSearchClicked") onSearchClicked: (OpenReason) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultFavouriteComponent
    }
}