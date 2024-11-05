package com.bignerdranch.android.weatherapp.presentation.details.elements.bottom_sheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BlockTitle(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(top = 16.dp)
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            modifier = modifier
                .size(20.dp),
            colorFilter = ColorFilter.tint(color = Color.Gray)
        )
        Text(
            text = text,
            color = Color.Gray.copy(alpha = 0.5f),
            fontSize = 12.sp,
            modifier = modifier.padding(start = 8.dp),
        )
    }
}
