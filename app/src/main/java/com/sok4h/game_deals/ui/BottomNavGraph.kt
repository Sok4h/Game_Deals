package com.sok4h.game_deals.ui


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sok4h.game_deals.ui.screens.DealScreen
import androidx.navigation.compose.navigation
import com.sok4h.game_deals.ui.screens.MainScreen
import com.sok4h.game_deals.ui.screens.WatchListScreen
import com.sok4h.game_deals.ui.viewModel.MainViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun BottomNavGraph(navHostController: NavHostController) {
    val uriHandler = LocalUriHandler.current
    val mainViewModel = getViewModel<MainViewModel>()
    val mainViewmodelState by mainViewModel.state.collectAsStateWithLifecycle()

    NavHost(
        navController = navHostController,
        startDestination = BottomBarScreens.Deals.route,
    ) {

        composable(route = BottomBarScreens.Games.route) {

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
            WatchListScreen(mainViewmodelState,
                onRemoveFromWatchList = { mainViewModel.removeGameFromWatchlist(it) },
                onDealPressed = {
                    uriHandler.openUri("https://www.cheapshark.com/redirect?dealID=${it}")
                })
        }

        composable(route = BottomBarScreens.Deals.route) {

            DealScreen(state = mainViewmodelState,
                onMinPriceChanged = { mainViewModel.updateMinPrice(price = it) },
                onMaxPriceChanged = { mainViewModel.updateMaxPrice(price = it) },
                onSortChanged = { mainViewModel.updateSortBy(it) },
                onFilterChanged = { mainViewModel.updateFilter() })
        }
    }

}