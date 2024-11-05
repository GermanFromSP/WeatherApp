package com.bignerdranch.android.weatherapp.presentation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.bignerdranch.android.weatherapp.domain.entity.City
import com.bignerdranch.android.weatherapp.presentation.details.DefaultDetailsComponent
import com.bignerdranch.android.weatherapp.presentation.favourite.DefaultFavouriteComponent
import com.bignerdranch.android.weatherapp.presentation.search.DefaultSearchComponent
import com.bignerdranch.android.weatherapp.presentation.search.OpenReason
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

class DefaultRootComponent @AssistedInject constructor(
    private val detailStoreFactory: DefaultDetailsComponent.Factory,
    private val favouriteStoreFactory: DefaultFavouriteComponent.Factory,
    private val searchStoreFactory: DefaultSearchComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Details(
            city = City(name = ""),
            openReason = OpenReason.SeeDetails
        ),
        childFactory = ::child,
        handleBackButton = false
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            is Config.Details -> {
                val component = detailStoreFactory.create(
                    city = config.city,
                    onBackClicked = {
                        if (config.isFirstRun)
                            navigation.push(Config.Favourite)
                        else navigation.pop()
                    },
                    componentContext = componentContext,
                    navigateToFavourite = {
                        navigation.bringToFront(Config.Favourite)
                    },
                    openReason = config.openReason
                )
                RootComponent.Child.Details(component)
            }

            Config.Favourite -> {

                val component = favouriteStoreFactory.create(
                    onCityItemClicked = {
                        navigation.push(
                            Config.Details(
                                city = it,
                                isFirstRun = false,
                                openReason = OpenReason.SeeDetails
                            )
                        )
                    },
                    onSearchClicked = {
                        navigation.push(Config.Search)
                    },
                    componentContext = componentContext
                )
                RootComponent.Child.Favourite(component)
            }

            is Config.Search -> {
                val component = searchStoreFactory.create(
                    componentContext = componentContext,
                    onBackClicked = { navigation.pop() },
                    onCitySavedToFavourite = { navigation.pop() },
                    onForecastForCityRequested = { city, openReason ->

                        navigation.push(
                            Config.Details(
                                city = city,
                                isFirstRun = false,
                                openReason = openReason
                            )
                        )
                    }
                )
                RootComponent.Child.Search(component)
            }
        }
    }

    sealed interface Config : Parcelable {

        @Parcelize
        data object Favourite : Config

        @Parcelize
        data object Search : Config

        @Parcelize
        data class Details(
            val city: City,
            val isFirstRun: Boolean = true,
            val openReason: OpenReason
        ) : Config
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,

            ): DefaultRootComponent
    }
}