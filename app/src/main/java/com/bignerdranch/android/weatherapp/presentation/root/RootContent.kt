package com.bignerdranch.android.weatherapp.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.bignerdranch.android.weatherapp.presentation.common.theme.WeatherAppTheme
import com.bignerdranch.android.weatherapp.presentation.details.DetailsContent
import com.bignerdranch.android.weatherapp.presentation.favourite.FavouriteContent
import com.bignerdranch.android.weatherapp.presentation.search.SearchContent

@Composable
fun RootContent(component: RootComponent) {

    WeatherAppTheme {
        Children(stack = component.stack,animation = stackAnimation( slide()),) {
            when (val instance = it.instance) {
                is RootComponent.Child.Details -> {
                    DetailsContent(component = instance.component)
                }

                is RootComponent.Child.Favourite -> {
                    FavouriteContent(component = instance.component)
                }

                is RootComponent.Child.Search -> {
                    SearchContent(component = instance.component)
                }
            }
        }
    }
}