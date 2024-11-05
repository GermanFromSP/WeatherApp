package com.bignerdranch.android.weatherapp.presentation.common

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bignerdranch.android.weatherapp.presentation.favourite.FavouriteCitiesListViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserStore(private val context: Context) {

    val viewStatusFlow: Flow<FavouriteCitiesListViewState> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preference ->
            val userStatus = preference[VIEW_STATUS] ?: 1

            when (userStatus) {
                1 -> FavouriteCitiesListViewState.LIST
                2 -> FavouriteCitiesListViewState.TABLE
                else -> FavouriteCitiesListViewState.INITIAL
            }
        }

    companion object {
        val Context.dataStore by preferencesDataStore("app_preferences")
        val VIEW_STATUS = intPreferencesKey("view_status")
    }

    suspend fun setViewStatus(viewStatus: FavouriteCitiesListViewState) {
        context.dataStore.edit { preferences ->
            preferences[VIEW_STATUS] = when(viewStatus) {
                FavouriteCitiesListViewState.LIST -> 1
                FavouriteCitiesListViewState.TABLE -> 2
                FavouriteCitiesListViewState.INITIAL -> 0
            }
        }
    }

}