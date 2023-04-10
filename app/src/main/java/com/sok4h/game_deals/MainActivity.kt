@file:OptIn(ExperimentalMaterial3Api::class)

package com.sok4h.game_deals

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sok4h.game_deals.ui.BottomBarScreens
import com.sok4h.game_deals.ui.BottomNavGraph
import com.sok4h.game_deals.ui.theme.Game_DealsTheme
import com.sok4h.game_deals.ui.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Game_DealsTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {

                            NavigationBar(Modifier.graphicsLayer {
                                shape = RoundedCornerShape(20.dp)
                                clip = true
                            }) {

                                val screens = listOf(

                                    BottomBarScreens.Home,
                                    BottomBarScreens.WatchList
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

                                            navController.navigate(screen.route)
                                        },
                                    )
                                }

                            }
                        }


                    ) {

                        Box(modifier = Modifier.padding(it)) {

                            BottomNavGraph(navHostController = navController)

                        }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun GameDealCardPreview() {

    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(Color.Yellow),

        ) {

        Row(
            Modifier
                .padding(vertical = 8.dp)
                .clickable {
                    expanded = !expanded
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                modifier = Modifier.size(80.dp),
                contentDescription = "",
                contentScale = ContentScale.Inside,
            )
            Column(
                modifier = Modifier
                    .background(Color.Red)
                    .padding(start = 4.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start

            ) {
                Box {
                    Text(
                        text = "God of War Ragnarók",
                        style = MaterialTheme.typography.bodyLarge,

                        )
                }



                Text(
                    text = " 4 Deals",
                    style = MaterialTheme.typography.bodySmall
                )


            }

            Text(text = "14.99", modifier = Modifier.padding(horizontal = 8.dp))
        }

        if (expanded) {
            for (i in 1..4) {

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Icon(imageVector = Icons.Default.AccountBox, contentDescription = "")
                    Text(text = "Epic Store")
                    Text(text = "3.55")

                    TextButton(onClick = { /*TODO*/ }) {

                        Text(text = "Comprar")
                    }
                }
            }
        }


    }

}


