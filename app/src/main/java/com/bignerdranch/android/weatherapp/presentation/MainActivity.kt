package com.bignerdranch.android.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.bignerdranch.android.weatherapp.WeatherApp
import com.bignerdranch.android.weatherapp.domain.usecase.ChangeFavouriteStateUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import com.bignerdranch.android.weatherapp.domain.usecase.SearchCityUseCase
import com.bignerdranch.android.weatherapp.presentation.root.DefaultRootComponent
import com.bignerdranch.android.weatherapp.presentation.root.RootContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {


    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as WeatherApp).applicationComponent.inject(this)
        super.onCreate(savedInstanceState)

        val root = rootComponentFactory.create(defaultComponentContext())

        setContent {
            RootContent(component = root)
        }
    }
}
