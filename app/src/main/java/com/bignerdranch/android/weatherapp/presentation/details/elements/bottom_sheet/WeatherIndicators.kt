package com.bignerdranch.android.weatherapp.presentation.details.elements.bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherIndicators(
    name: String,
    indicator: String
) {
    val textColor = MaterialTheme.colorScheme.onSurface

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(text = name, fontSize = 14.sp, color = textColor)
        Text(text = indicator, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textColor)
    }
}