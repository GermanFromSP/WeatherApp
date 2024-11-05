package com.bignerdranch.android.weatherapp.presentation.details.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
fun DetailsCard(
    modifier: Modifier = Modifier,
    titleIcon: ImageVector,
    titleText: String,
    content: @Composable() ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray.copy(
                alpha = 0.7f
            )
        )
    ) {
        CardTitle(icon = titleIcon, text = titleText)
        content()
    }
}

@Composable
fun CardTitle(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                imageVector = icon,
                contentDescription = null,
                modifier = modifier
                    .size(30.dp)
                    .padding(top = 8.dp, start = 16.dp),
                colorFilter = ColorFilter.tint(color = Color.LightGray)
            )
            Text(
                text = text,
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 12.sp,
                modifier = modifier.padding(top = 8.dp, start = 8.dp)
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .height(1.dp)
                .background(color = Color.Gray)
        )
    }
}