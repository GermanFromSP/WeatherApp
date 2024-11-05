package com.bignerdranch.android.weatherapp.presentation.details.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.domain.entity.ForecastWeather
import com.bignerdranch.android.weatherapp.presentation.common.extensions.formattedShortDayOfWeek
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun AnimatedUpcomingWeather(
    upcoming: List<ForecastWeather>,
    onDayClick: (Int) -> Unit,
    showBottomSheet: () -> Unit
) {

    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(animationSpec = tween(500)) + slideIn(
            animationSpec = tween(500),
            initialOffset = { IntOffset(0, it.height) }
        )
    ) {
        UpcomingWeather(
            upcoming = upcoming,
            onDayClick = { onDayClick(it) },
            showBottomSheet = {showBottomSheet()}
        )
    }
}

@Composable
private fun UpcomingWeather(
    upcoming: List<ForecastWeather>,
    onDayClick: (Int) -> Unit,
    showBottomSheet: () -> Unit
) {
    DetailsCard(
        titleIcon = Icons.Default.CalendarMonth,
        titleText = stringResource(id = R.string.three_days_weather_forecast),
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 8.dp)
    ) {
        UpcomingContent(
            upcoming = upcoming,
            onDayClick = { onDayClick(it) },
            showBottomSheet = {showBottomSheet()}
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun UpcomingContent(
    modifier: Modifier = Modifier,
    upcoming: List<ForecastWeather>,
    onDayClick: (Int) -> Unit,
    showBottomSheet: () -> Unit
) {
    val textColor = MaterialTheme.colorScheme.onBackground

    upcoming.forEachIndexed { index, upcomingItem ->

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(start = 24.dp, end = 24.dp)
                .clickable {
                    onDayClick(index)
                    showBottomSheet()
                }
        ) {
            Text(
                text = if (index == 0) stringResource(id = R.string.forecast_day_today)
                else upcomingItem.date.formattedShortDayOfWeek(),
                modifier = modifier.weight(1f),
                color = textColor
            )

            GlideImage(
                model = upcomingItem.conditionImageUrl,
                contentDescription = null,
            )
            Spacer(modifier = modifier.weight(1f))
            Text(text = upcomingItem.minTempC + "..",
                color = textColor)
            Text(text = ".. " + upcomingItem.maxTempC,
                color = textColor)
        }
        if (index != 2) {
            Spacer(
                modifier = modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color.Gray)
            )
        }
    }
}
