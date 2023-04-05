package com.sok4h.game_deals.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sok4h.game_deals.events.MainScreenEvents
import com.sok4h.game_deals.ui.components.GameDealCard
import com.sok4h.game_deals.ui.components.SearchBar
import com.sok4h.game_deals.ui.viewModel.MainViewModel
import com.sok4h.game_deals.utils.DealState
import com.sok4h.game_deals.utils.GameState

@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    val uiState by viewModel.state.collectAsState()

    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        SearchBar(
            textValue = uiState.searchQuery,
            onQueryChanged = { viewModel.updateQuery(it) },
            onSearch = { viewModel.setStateEvent(MainScreenEvents.SearchGames) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        when (val state = uiState.gameListState) {

            is GameState.Error -> {}
            GameState.Loading -> {

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(25.dp),
                        strokeWidth = 2.dp, color = Color.Red
                    )
                }

            }
            is GameState.Success -> {

                Log.e("TAG", "MainScreen: " )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxHeight(0.8f)
                ) {

                    items(items = state.data) { gameItem ->

                        GameDealCard(
                            game = gameItem,
                            onAddToWatchList = {
                                viewModel.setStateEvent(MainScreenEvents.AddGametoWatchList(it))
                            },
                            onRemoveFromWatchList = {

                                Log.e("TAG", "Eliminado" )
                                viewModel.setStateEvent(
                                    MainScreenEvents.RemoveFromWatchList(
                                        it
                                    )
                                )
                            },
                            onDealPressed = {


                            }
                        )
                        Divider(thickness = 1.dp)


                    }
                }

            }

        }

        when (uiState.dealListState) {
            is DealState.Error -> {

                Text(text = (uiState.dealListState as DealState.Error).error.message ?: "xd")
            }
            DealState.Loading -> Text(text = "Cargando")
            is DealState.Success -> Text(text = "Exito")
        }

    }


}