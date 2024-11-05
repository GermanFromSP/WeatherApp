package com.bignerdranch.android.weatherapp.presentation.details

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

class DefaultDetailsComponent @AssistedInject constructor(
    private val detailStoreFactory: DetailStoreFactory,
    @Assisted("city") private val city: City,
    @Assisted("onBackClicked") private val onBackClicked: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("navigateToFavourite") private val navigateToFavourite: () -> Unit,
    @Assisted("openReason") private val openReason: OpenReason
) : DetailsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { detailStoreFactory.create(city, openReason = openReason) }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    DetailStore.Label.ClickBack -> {
                        onBackClicked()
                    }

                    DetailStore.Label.NavigateToFavourite -> {
                        navigateToFavourite()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<DetailStore.State> = store.stateFlow

    override fun onClickBack() {
        store.accept(DetailStore.Intent.ClickBack)
    }

    override fun onClickChangeFavouriteStatus() {
        store.accept(DetailStore.Intent.ClickChangeFavouriteStatus)
    }

    override fun onClickUpdate() {
        store.accept(DetailStore.Intent.ClickUpdate)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("city") city: City,
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("navigateToFavourite") navigateToFavourite: () -> Unit,
            @Assisted("openReason") openReason: OpenReason
        ): DefaultDetailsComponent
    }
}