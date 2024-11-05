package com.bignerdranch.android.weatherapp.presentation.common

import android.content.SharedPreferences
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

fun SharedPreferences.Editor.putParcelable(key: String, parcelable: Parcelable?) {
    val json = Gson().toJson(parcelable)
    putString(key, json)
}

inline fun <reified T : Parcelable?> SharedPreferences.getParcelable(key: String, default: T): T {
    val json = getString(key, null)
    return try {
        if (json != null)
            Gson().fromJson(json, T::class.java)
        else default
    } catch (_: JsonSyntaxException) {
        default
    }
}

fun SharedPreferences.Editor.putParcelableList(key: String, collection: Collection<Parcelable>) {
    val json = Gson().toJson(collection)
    putString(key, json)
}

inline fun <reified T : Parcelable?> SharedPreferences.getParcelableList(
    key: String,
    default: List<T>
): List<T> {
    val json = getString(key, null)
    return try {
        if (json != null) {
            val result = arrayListOf<T>()
            for (e in Gson().fromJson(json, Collection::class.java))
                if (e is T)
                    result.add(e)
            result
        } else default
    } catch (_: JsonSyntaxException) {
        default
    }
}