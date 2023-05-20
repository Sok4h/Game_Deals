package com.sok4h.game_deals.ui


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sok4h.game_deals.ui.components.GameDealCard
import com.sok4h.game_deals.ui.screens.DealScreen
import com.sok4h.game_deals.ui.screens.WatchListScreen
import com.sok4h.game_deals.ui.viewModel.MainViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavGraph(navHostController: NavHostController) {
    val mainViewModel = getViewModel<MainViewModel>()
    val mainViewmodelState by mainViewModel.state.collectAsStateWithLifecycle()

    Column {

        NavHost(
            navController = navHostController,
            startDestination = BottomBarScreens.Deals.route,
        ) {
            composable(route = BottomBarScreens.WatchList.route) {

                var activeBar by remember { mutableStateOf(false) }
                Column {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {


                        SearchBar(
                            modifier = Modifier,
                            query = mainViewmodelState.searchQuery,
                            onQueryChange = { mainViewModel.updateQuery(it) },
                            onSearch = { mainViewModel.searchGame() },
                            active = activeBar,
                            placeholder = { Text(text = "Search Game") },
                            leadingIcon = {
                                if (activeBar) {
                                    IconButton(onClick = { activeBar = false }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            },
                            trailingIcon = {
                                if (mainViewmodelState.searchQuery.isNotEmpty() && activeBar) {
                                    IconButton(onClick = { mainViewModel.searchGame() }) {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "Search Icon"
                                        )
                                    }

                                }
                            },


                            onActiveChange = { activeBar = !activeBar }
                        ) {

                            Column(
                                Modifier
                                    /* .verticalScroll(state = scrollState)*/
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                if (mainViewmodelState.gameListState.isNotEmpty()) {

                                    LazyColumn(
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.fillMaxHeight()
                                    ) {

                                        items(items = mainViewmodelState.gameListState) { gameItem ->

                                            GameDealCard(game = gameItem, onAddToWatchList = {
                                                mainViewModel.addGameToWatchList(it)
                                            }, onRemoveFromWatchList = {
                                                mainViewModel.removeGameFromWatchlist(
                                                    id = it
                                                )
                                            })

                                        }
                                    }

                                }

                                if (mainViewmodelState.isGameLoading) {

                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        CircularProgressIndicator(
                                            modifier = Modifier.size(25.dp), strokeWidth = 2.dp,
                                        )
                                    }

                                }

                                if (mainViewmodelState.gameListErrorMessage.isNotEmpty()) {
                                    Text(text = mainViewmodelState.gameListErrorMessage)
                                }
                            }
                        }
                    }
                    WatchListScreen(
                        mainViewmodelState,
                        onRemoveFromWatchList = { mainViewModel.removeGameFromWatchlist(it) },

                        )
                }
            }

            composable(route = BottomBarScreens.Deals.route) {

                var activeBar by remember { mutableStateOf(false) }
                Column {


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {


                        SearchBar(
                            query = mainViewmodelState.searchQuery,
                            onQueryChange = { mainViewModel.updateQuery(it) },
                            onSearch = { mainViewModel.searchGame() },
                            active = activeBar,
                            placeholder = { Text(text = "Search Game") },
                            leadingIcon = {
                                if (activeBar) {
                                    IconButton(onClick = { activeBar = false }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            },
                            trailingIcon = {
                                if (mainViewmodelState.searchQuery.isNotEmpty() && activeBar) {
                                    IconButton(onClick = { mainViewModel.searchGame() }) {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "Search Icon"
                                        )
                                    }

                                }
                            },

                            onActiveChange = { activeBar = !activeBar }
                        ) {

                            Column(
                                Modifier
                                    /* .verticalScroll(state = scrollState)*/
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                if (mainViewmodelState.gameListState.isNotEmpty()) {

                                    LazyColumn(
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.fillMaxHeight()
                                    ) {

                                        items(items = mainViewmodelState.gameListState) { gameItem ->

                                            GameDealCard(game = gameItem, onAddToWatchList = {
                                                mainViewModel.addGameToWatchList(it)
                                            }, onRemoveFromWatchList = {
                                                mainViewModel.removeGameFromWatchlist(
                                                    id = it
                                                )
                                            })

                                        }
                                    }

                                }

                                if (mainViewmodelState.isGameLoading) {

                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        CircularProgressIndicator(
                                            modifier = Modifier.size(25.dp), strokeWidth = 2.dp,
                                        )
                                    }

                                }

                                if (mainViewmodelState.gameListErrorMessage.isNotEmpty()) {
                                    Text(text = mainViewmodelState.gameListErrorMessage)
                                }
                            }
                        }
                    }
                    DealScreen(state = mainViewmodelState,
                        onMinPriceChanged = { mainViewModel.updateMinPrice(price = it) },
                        onMaxPriceChanged = { mainViewModel.updateMaxPrice(price = it) },
                        onSortChanged = { mainViewModel.updateSortBy(it) },
                        onFilterChanged = { mainViewModel.updateFilter() })
                }
            }
        }
    }
}