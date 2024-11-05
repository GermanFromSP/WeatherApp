package com.bignerdranch.android.weatherapp.presentation.search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.weatherapp.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchContent(component: SearchComponent) {

    val state by component.model.collectAsState()

    val focusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    var query by rememberSaveable {
        mutableStateOf(state.searchQuery)
    }

    var searchActiveState by rememberSaveable {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background))

    SearchBar(
        modifier = Modifier
            .focusRequester(focusRequester)
            .padding(if (!searchActiveState) 8.dp else 0.dp)
            .animateContentSize(),
        query = query,
        onQueryChange = {
            searchActiveState = true
            query = it
            component.changeSearchQuery(it)
        },
        onSearch = { component.onClickSearch() },
        active = searchActiveState,
        shape = ShapeDefaults.ExtraLarge,
        onActiveChange = {

        },
        leadingIcon = {
            IconButton(onClick = { component.onClickBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = { component.onClickSearch() }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            when (val searchState = state.searchState) {
                SearchStore.State.SearchState.EmptyResult -> {
                    Text(
                        text = stringResource(R.string.search_empty_result),
                        modifier = Modifier.padding(8.dp)
                    )
                }

                SearchStore.State.SearchState.Error -> {
                    Text(
                        text = stringResource(R.string.whats_went_wrong),
                        modifier = Modifier.padding(8.dp)
                    )
                }

                SearchStore.State.SearchState.Initial -> {
                    Initial()
                }

                SearchStore.State.SearchState.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                is SearchStore.State.SearchState.SuccessLoaded -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(
                            items = searchState.cities,
                            key = { it.city.id }
                        ) {
                            FoundCityCard(
                                foundCityItem = it,
                                modifier = Modifier.animateItemPlacement(),
                                onCityClick = { component.onClickCity(it.city) },
                                onLongClick = {}
                            )
                        }
                    }
                }
            }
        }

    }

}


@Composable
private fun Initial() {
}