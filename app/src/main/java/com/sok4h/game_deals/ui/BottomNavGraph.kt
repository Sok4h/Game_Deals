package com.sok4h.game_deals.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.sok4h.game_deals.ui.screens.DealScreen
import com.sok4h.game_deals.ui.screens.MainScreen
import com.sok4h.game_deals.ui.screens.WatchListScreen
import com.sok4h.game_deals.ui.viewModel.MainViewModel
import com.sok4h.game_deals.ui.viewModel.WatchListViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun BottomNavGraph(navHostController: NavHostController) {
    val uriHandler = LocalUriHandler.current

    val mainViewModel = getViewModel<MainViewModel>()

    val watchListViewModel = getViewModel<WatchListViewModel>()

    val watchListState by watchListViewModel.state.collectAsStateWithLifecycle()

    val mainViewmodelState by mainViewModel.state.collectAsStateWithLifecycle()

    AnimatedNavHost(navController = navHostController, startDestination = "home") {

        composable(route = BottomBarScreens.Home.route, exitTransition = {->

            fadeOut(animationSpec = tween(300))

        }) {

            MainScreen(state = mainViewmodelState,
                onQueryChanged = { mainViewModel.updateQuery(query = it) },
                onGameSearch = { mainViewModel.searchGame() },
                onGameAddedToWatchList = { mainViewModel.addGameToWatchList(it) },
                onGameRemovedWatchList = { mainViewModel.removeGameFromWatchlist(it) },
                onDealPressed = {
                    uriHandler.openUri("https://www.cheapshark.com/redirect?dealID=${it}")

                },
                onNavToRecentDeal = { navHostController.navigate(route = "DealScreen") })

        }

        composable(route = BottomBarScreens.WatchList.route) {
            WatchListScreen(watchListState,
                onRemoveFromWatchList = { mainViewModel.removeGameFromWatchlist(it) },
                onDealPressed = {
                    uriHandler.openUri("https://www.cheapshark.com/redirect?dealID=${it}")
                })
        }

        composable(route = "DealScreen") {

            DealScreen(state = mainViewmodelState,
                onMinPriceChanged = { mainViewModel.updateMinPrice(price = it) },
                onMaxPriceChanged = { mainViewModel.updateMaxPrice(price = it) },
                onSortChanged = { mainViewModel.updateSortBy(it) },
                onFilterChanged = { mainViewModel.updateFilter() })
        }
    }

}