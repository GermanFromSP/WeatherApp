package com.bignerdranch.android.weatherapp.presentation.details.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.Waves
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weatherapp.R
import com.bignerdranch.android.weatherapp.presentation.common.extensions.tempToFormattedString

@Composable
fun HumidityContent(modifier: Modifier = Modifier, humidity: Int, dewPoint: Float) {

    val textModifier = Modifier
    DetailsCard(
        titleIcon = Icons.Default.Waves,
        titleText = stringResource(id = R.string.humidity),
        modifier = modifier.padding(end = 8.dp)
    ) {

        val textColor = MaterialTheme.colorScheme.onBackground

        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$humidity",
                    fontSize = 48.sp,
                    modifier = textModifier
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    color = textColor
                )
                Text(text = stringResource(R.string.percent),
                    color = textColor)
                Spacer(modifier = Modifier.weight(1f))
            }

            Text(
                text = stringResource(id = R.string.dew_point_now) + dewPoint.tempToFormattedString(),
                fontSize = 12.sp,
                modifier = textModifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                textAlign = TextAlign.Center,
                color = textColor
            )
            Spacer(modifier = Modifier.weight(1f))
        }


    }

}

@Composable
fun PressureContent(modifier: Modifier = Modifier, pressureMmM: Int) {

    val textColor = MaterialTheme.colorScheme.onBackground

    val textModifier = Modifier
    DetailsCard(
        titleIcon = Icons.Default.Compress,
        titleText = stringResource(id = R.string.pressure_title),
        modifier = modifier.padding(start = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDownward,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(32.dp),
                tint = textColor
            )
        }
        Text(
            text = pressureMmM.toString(),
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            modifier = textModifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            color = textColor
        )
        Text(
            text = stringResource(id = R.string.mm_hg),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = textModifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            color = textColor
        )
    }

}