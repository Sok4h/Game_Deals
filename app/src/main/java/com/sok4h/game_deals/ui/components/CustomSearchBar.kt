package com.sok4h.game_deals.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sok4h.game_deals.R
import com.sok4h.game_deals.ui.ui_model.GameDetailModel
import com.sok4h.game_deals.ui.viewStates.MainScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    mainViewmodelState: MainScreenState,
    onSearch: () -> Unit,
    onQueryUpdate: (String) -> Unit,
    onAddGameToWatchlist: (GameDetailModel) -> Unit,
    onRemoveFromWatchlist: (String) -> Unit,
    isActive: Boolean,
    onActiveChange: (Boolean) -> Unit
) {
    Column {

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {


            SearchBar(modifier = Modifier,
                query = mainViewmodelState.searchQuery,
                onQueryChange = { onQueryUpdate(it) },
                onSearch = { onSearch() },
                active = isActive,
                placeholder = { Text(text = stringResource(R.string.search_game)) },
                leadingIcon = {
                    if (isActive) {
                        IconButton(onClick = { onActiveChange(false) }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack, contentDescription = ""
                            )
                        }
                    }
                },
                trailingIcon = {

                    IconButton(onClick = {

                        if (mainViewmodelState.searchQuery.isNotEmpty() && isActive) {

                            onSearch()
                        } else {
                            if (!isActive) {
                                onActiveChange(true)
                            }
                        }

                    })

                    {
                        Icon(
                            imageVector = Icons.Default.Search, contentDescription = "Search Icon"
                        )
                    }
                },
                onActiveChange = { onActiveChange(it)}) {

                Column(
                    Modifier.padding(
                        16.dp
                    ), horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    if (mainViewmodelState.gameListState.isNotEmpty()) {

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxHeight()
                        ) {

                            items(items = mainViewmodelState.gameListState) { gameItem ->

                                GameDealCard(game = gameItem, onAddToWatchList = {
                                    onAddGameToWatchlist(it)
                                }, onRemoveFromWatchList = {
                                    onRemoveFromWatchlist(it)
                                })

                            }
                        }

                    }

                    if (mainViewmodelState.isGameLoading) {

                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {

                            CircularProgressIndicator(
                                modifier = Modifier.size(25.dp),
                                strokeWidth = 2.dp,
                            )
                        }

                    }

                    if (mainViewmodelState.gameListError.isNotEmpty()) {
                        Text(text = mainViewmodelState.gameListError)
                    }
                }
            }
        }
    }
}


