package com.sok4h.game_deals.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sok4h.game_deals.ui.screens.MainScreen
import com.sok4h.game_deals.ui.screens.WatchListScreen
import com.sok4h.game_deals.ui.viewModel.MainViewModel
import com.sok4h.game_deals.ui.viewModel.WatchListViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavGraph(navHostController: NavHostController) {
    val uriHandler = LocalUriHandler.current

    val mainViewModel = getViewModel<MainViewModel>()

    val watchListViewModel = getViewModel<WatchListViewModel>()

    val watchListState by watchListViewModel.state.collectAsStateWithLifecycle()

    val mainViewmodelState by mainViewModel.state.collectAsStateWithLifecycle()

    NavHost(navController = navHostController, startDestination = "home") {

        composable(route = BottomBarScreens.Home.route) {

            MainScreen(
                state = mainViewmodelState,
                onQueryChanged = { mainViewModel.updateQuery(query = it) },
                onGameSearch = { mainViewModel.searchGame() },
                onGameAddedToWatchList = { mainViewModel.addGameToWatchList(it) },
                onGameRemovedWatchList = { mainViewModel.removeGameFromWatchlist(it) },
                onDealPressed = {
                    uriHandler.openUri("https://www.cheapshark.com/redirect?dealID=${it}")

                }
            , onSortChanged = {mainViewModel.updateSortBy(it)}
            )

        }

        composable(route = BottomBarScreens.WatchList.route) {

            WatchListScreen(
                watchListState,
                onRemoveFromWatchList = { mainViewModel.removeGameFromWatchlist(it) },
                onDealPressed = {
                    uriHandler.openUri("https://www.cheapshark.com/redirect?dealID=${it}")
                })
        }
    }

}