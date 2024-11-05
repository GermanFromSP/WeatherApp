package com.bignerdranch.android.weatherapp.presentation.favourite.elements

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderAll
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weatherapp.R

@Composable
fun DeleteIcon(size: Dp, onIconClick: () -> Unit) {

    Icon(
        imageVector = Icons.Default.RemoveCircle,
        contentDescription = null,
        tint = Color.Red.copy(alpha = 0.5f),
        modifier = Modifier
            .size(size)
            .clickable { onIconClick() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarFavouriteScreen(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    onListClick: () -> Unit,
    onTableClick: () -> Unit,
    onDeleteClick: () -> Unit,
    deleteState: Boolean,
    canDelete: Boolean,
    onDoneClick: () -> Unit
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    val haptic = LocalHapticFeedback.current

    MediumTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface
            )
        },

        actions = {

            AnimatedContent(targetState = deleteState, label = "") { deleteState ->
                if (deleteState) {
                    Text(
                        text = stringResource(R.string.done),
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .clickable { onDoneClick() })
                } else {

                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { expanded = !expanded }
                    )

                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {

                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.view)) },
                            onClick = {}
                        )
                        HorizontalDivider()
                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.list)) },
                            onClick = {
                                expanded = false
                                onListClick()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.List,
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.table)) },
                            onClick = {
                                expanded = false
                                onTableClick()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.BorderAll,
                                    contentDescription = null
                                )
                            }
                        )
                        HorizontalDivider()
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.delete),
                                    color = if (canDelete) MaterialTheme.colorScheme.onBackground else Color.LightGray
                                )
                            },
                            onClick = {
                                if (!canDelete) {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                } else {
                                    expanded = false
                                    onDeleteClick()
                                }
                            },
                        )
                    }
                }
            }

        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        scrollBehavior = scrollBehavior
    )
}
