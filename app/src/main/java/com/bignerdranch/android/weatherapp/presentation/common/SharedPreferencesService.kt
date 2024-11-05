package com.bignerdranch.android.weatherapp.presentation.common

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import androidx.core.content.edit
import com.bignerdranch.android.weatherapp.domain.entity.City
import com.bignerdranch.android.weatherapp.presentation.MainActivity.Companion.SHARED_PREF_NAME

class SharedPreferencesService(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun saveData(key: String, parcelable: Parcelable?) {
        sharedPreferences.edit {
            putParcelable(key, parcelable)
        }
    }

    fun getData(key: String, defaultValue: City?): City? {
        return sharedPreferences.getParcelable(key, defaultValue)
    }
}