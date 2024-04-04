package com.bignerdranch.android.weatherapp.di

import android.content.Context
import com.bignerdranch.android.weatherapp.data.local.db.FavouriteCitiesDao
import com.bignerdranch.android.weatherapp.data.local.db.FavouriteDataBase
import com.bignerdranch.android.weatherapp.data.network.api.ApiFactory
import com.bignerdranch.android.weatherapp.data.network.api.ApiService
import com.bignerdranch.android.weatherapp.data.repository.FavouriteRepositoryImpl
import com.bignerdranch.android.weatherapp.data.repository.SearchRepositoryImpl
import com.bignerdranch.android.weatherapp.data.repository.WeatherRepositoryImpl
import com.bignerdranch.android.weatherapp.domain.repository.FavouriteRepository
import com.bignerdranch.android.weatherapp.domain.repository.SearchRepository
import com.bignerdranch.android.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @[ApplicationScope Binds]
    fun bindFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository

    @[ApplicationScope Binds]
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @[ApplicationScope Binds]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    companion object {

        @[ApplicationScope Provides]
        fun provideApiService(): ApiService = ApiFactory.apiService

        @[ApplicationScope Provides]
        fun provideFavouriteDatabase(context: Context): FavouriteDataBase {
          return  FavouriteDataBase.getInstance(context)
        }

        @[ApplicationScope Provides]
        fun provideFavouriteCitiesDao(dataBase: FavouriteDataBase): FavouriteCitiesDao {
            return  dataBase.favouriteCitiesDao()
        }
    }
}