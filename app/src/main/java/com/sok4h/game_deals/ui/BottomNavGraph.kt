package com.sok4h.game_deals.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sok4h.game_deals.ui.screens.DealScreen
import com.sok4h.game_deals.ui.screens.WatchListScreen
import com.sok4h.game_deals.ui.viewModel.MainViewModel
import com.sok4h.game_deals.ui.viewStates.MainScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavGraph(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    mainViewModelState: MainScreenState
) {

    Column {

        NavHost(
            navController = navHostController,
            startDestination = BottomBarScreens.Deals.route,
        ) {


            composable(route = BottomBarScreens.Favorites.route) {

                WatchListScreen(
                    mainViewModelState,
                    onRemoveFromWatchList = { mainViewModel.removeGameFromWatchlist(it) },
                    onAutoStartShown = {mainViewModel.updatePreference()}
                    )
            }

            composable(route = BottomBarScreens.Deals.route) {


                DealScreen(state = mainViewModelState,
                    onMinPriceChanged = { mainViewModel.updateMinPrice(price = it) },
                    onMaxPriceChanged = { mainViewModel.updateMaxPrice(price = it) },
                    onSortChanged = { sort, id ->

                        mainViewModel.updateSortBy(sort, id)
                    },
                    onFilterChanged = { mainViewModel.updateFilter() },
                    onScrollChanged = { mainViewModel.onDealScrollChanged(it) },
                    onChangePage = { mainViewModel.changePage() })
            }
        }
    }
}
