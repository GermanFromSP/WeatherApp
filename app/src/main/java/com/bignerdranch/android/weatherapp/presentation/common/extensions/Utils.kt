package com.bignerdranch.android.weatherapp.presentation.common.extensions

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.bignerdranch.android.weatherapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

fun ComponentContext.componentScope() = CoroutineScope(
    Dispatchers.Main.immediate + SupervisorJob()
).apply {
    lifecycle.doOnDestroy { cancel() }
}


fun Int.getAssessmentOfVisibilityStringResId(): Int {
    return when (this) {
        in 0.. 1 -> R.string.very_bad_visibility
        in 1..2 -> R.string.bad_visibility
        in 2..10 -> R.string.medium_visibility
        in 10..20 -> R.string.good_visibility
        in 20..50 -> R.string.perfect_visibility
        else -> R.string.perfect_visibility
    }
}

fun Float.tempToFormattedString() = "${roundToInt()}°"

fun Calendar.formattedFullDay(): String {
    val format = SimpleDateFormat("EEEE | d MMM y", Locale.getDefault())
    return format.format(time)
}

fun Calendar.formattedShortDayOfWeek(): String {
    val format = SimpleDateFormat("EEE", Locale.getDefault())
    return format.format(time)
}