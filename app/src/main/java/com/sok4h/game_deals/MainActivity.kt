package com.sok4h.game_deals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sok4h.game_deals.ui.BottomBarScreens
import com.sok4h.game_deals.ui.BottomNavGraph
import com.sok4h.game_deals.ui.components.CustomSearchBar
import com.sok4h.game_deals.ui.components.GameDealCard
import com.sok4h.game_deals.ui.theme.Game_DealsTheme
import com.sok4h.game_deals.ui.viewModel.MainViewModel
import org.koin.androidx.compose.getViewModel


class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Game_DealsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val mainViewModel = getViewModel<MainViewModel>()
                    val mainViewmodelState by mainViewModel.state.collectAsStateWithLifecycle()


                    Scaffold(
                        bottomBar = {
                            NavigationBar(
                                Modifier.graphicsLayer {
                                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                                    clip = true
                                },
                            ) {

                                val screens = listOf(

                                    BottomBarScreens.Deals,
                                    BottomBarScreens.Favorites,

                                    )

                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination

                                screens.forEach { screen ->

                                    NavigationBarItem(
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        label = { Text(text = stringResource(id = screen.title)) },
                                        icon = {
                                            Icon(
                                                imageVector = screen.icon,
                                                contentDescription = "icon of ${screen.route}"
                                            )
                                        },
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                            }
                                        },
                                    )
                                }

                            }
                        },

                        ) {

                        Column(modifier = Modifier.padding(it)) {

                            CustomSearchBar(
                                mainViewmodelState = mainViewmodelState,
                                onSearch = { mainViewModel.searchGame() },
                                onQueryUpdate = { mainViewModel.updateQuery(it) },
                                onAddGameToWatchlist = { game ->
                                    mainViewModel.addGameToWatchList(
                                        game
                                    )
                                },
                                onRemoveFromWatchlist = { id ->
                                    mainViewModel.removeGameFromWatchlist(
                                        id
                                    )
                                }
                            )
                            BottomNavGraph(
                                navHostController = navController,
                                mainViewModel,
                                mainViewmodelState
                            )

                        }
                    }

                }
            }
        }
    }
}

