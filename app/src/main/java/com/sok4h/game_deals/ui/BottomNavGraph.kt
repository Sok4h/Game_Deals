package com.sok4h.game_deals.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun BottomNavGraph (navHostController: NavHostController) {

    val mainViewModel = getViewModel<MainViewModel>()

    val watchListViewModel = getViewModel<WatchListViewModel>()

    val watchListState by watchListViewModel.state.collectAsState()

    NavHost(navController = navHostController, startDestination = "home",) {

        composable(route = BottomBarScreens.Home.route){

            MainScreen(viewModel = mainViewModel)

        }

        composable(route = BottomBarScreens.WatchList.route){
            
            WatchListScreen(watchListState, onEvent ={watchListViewModel.setEvent(it)})
        }
    }
    
}