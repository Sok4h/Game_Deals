package com.sok4h.game_deals

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sok4h.game_deals.ui.BottomBarScreens
import com.sok4h.game_deals.ui.BottomNavGraph
import com.sok4h.game_deals.ui.theme.Game_DealsTheme


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Game_DealsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()


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
                                    BottomBarScreens.WatchList,

                                    )

                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination

                                screens.forEach { screen ->

                                    NavigationBarItem(
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        label = { Text(text = screen.title) },
                                        icon = {
                                            Icon(
                                                imageVector = screen.icon,
                                                contentDescription = "icon of ${screen.title}"
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
                            BottomNavGraph(navHostController = navController)


                        }
                    }

                }
            }
        }
    }
}

