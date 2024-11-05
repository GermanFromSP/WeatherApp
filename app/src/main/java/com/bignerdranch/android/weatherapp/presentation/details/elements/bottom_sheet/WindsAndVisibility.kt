package com.bignerdranch.android.weatherapp.presentation.details.elements.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.presentation.common.extensions.getAssessmentOfVisibilityStringResId


@Composable
fun WindAndVisibilityBlock(
    modifier: Modifier = Modifier,
    avgWindSpeed: String,
    visibility: Int
) {

    val textColor = MaterialTheme.colorScheme.onSurface

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = avgWindSpeed, fontSize = 42.sp, color = textColor)
                Column(
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Text(text = stringResource(id = R.string.m), fontSize = 12.sp, color = textColor)
                    Spacer(
                        modifier = Modifier
                            .width(10.dp)
                            .height(1.dp)
                            .background(Color.Gray)
                    )
                    Text(text = stringResource(id = R.string.s), fontSize = 12.sp, color = textColor)
                }
            }

            Text(text = stringResource(id = R.string.avg_wind_speed), fontSize = 12.sp, color = textColor)
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = visibility.toString() + stringResource(id = R.string.km), fontSize = 42.sp, color = textColor)
            Text(
                text = stringResource(id = visibility.getAssessmentOfVisibilityStringResId()),
                fontSize = 12.sp, color = textColor
            )
        }
    }
}
