package com.bignerdranch.android.weatherapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bignerdranch.android.weatherapp.data.network.api.ApiFactory
import com.bignerdranch.android.weatherapp.presentation.ui.theme.WeatherAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = ApiFactory.apiService

        CoroutineScope(Dispatchers.Main).launch {
            val currentWeather = apiService.loadCurrentWeather("Moscow")
            val forecast = apiService.loadForecast("Moscow")
            val cities = apiService.searchCity("Moscow")

            Log.d("MainActivity", "weather: $currentWeather ")
            Log.d("MainActivity", "forecast: $forecast ")
            Log.d("MainActivity", "cities: $cities ")
        }
        setContent {

        }
    }
}
