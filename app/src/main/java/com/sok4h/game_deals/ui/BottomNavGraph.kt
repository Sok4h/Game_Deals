package com.sok4h.game_deals.ui


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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sok4h.game_deals.R
import com.sok4h.game_deals.ui.components.GameDealCard
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

                    )

            }

            composable(route = BottomBarScreens.Deals.route) {


                DealScreen(state = mainViewModelState,
                    onMinPriceChanged = { mainViewModel.updateMinPrice(price = it) },
                    onMaxPriceChanged = { mainViewModel.updateMaxPrice(price = it) },
                    onSortChanged = { sort, id ->

                        mainViewModel.updateSortBy(sort, id)
                    },
                    onFilterChanged = { mainViewModel.updateFilter() })
            }
        }
    }
}
