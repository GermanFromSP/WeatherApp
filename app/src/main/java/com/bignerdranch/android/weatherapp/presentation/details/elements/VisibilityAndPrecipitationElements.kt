package com.bignerdranch.android.weatherapp.presentation.details.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.WaterDrop
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
import com.bignerdranch.android.weatherapp.presentation.common.extensions.getAssessmentOfVisibilityStringResId

@Composable
fun VisibilityContent(modifier: Modifier = Modifier, visibilityKm: Int) {
    val textColor = MaterialTheme.colorScheme.onBackground
    DetailsCard(
        titleIcon = Icons.Default.RemoveRedEye,
        titleText = stringResource(id = R.string.visibility_title),
        modifier = modifier.padding(end = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = visibilityKm.toString() + stringResource(id = R.string.km),
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                color = textColor
            )

            Text(
                text = stringResource(id = visibilityKm.getAssessmentOfVisibilityStringResId()),
                fontSize = 12.sp,
                lineHeight = 15.sp,
                modifier = Modifier.padding(8.dp),
                color = textColor
            )
            Spacer(modifier = Modifier.weight(1f))
        }

    }
}

@Composable
fun PrecipitationContent(modifier: Modifier = Modifier, precipitationMm: Int) {

    val textColor = MaterialTheme.colorScheme.onBackground

    DetailsCard(
        titleIcon = Icons.Default.WaterDrop,
        titleText = stringResource(id = R.string.precipitations),
        modifier = modifier.padding(start = 8.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = precipitationMm.toString() + stringResource(id = R.string.mm),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            color = textColor
        )
        Text(
            text = stringResource(id = R.string.for_the_last_24h),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp),
            color = textColor
        )
        Spacer(modifier = Modifier.weight(1f))
    }

}