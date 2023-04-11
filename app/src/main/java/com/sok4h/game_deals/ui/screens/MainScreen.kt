package com.sok4h.game_deals.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import com.sok4h.game_deals.ui.components.DealCard
import com.sok4h.game_deals.ui.components.GameDealCard
import com.sok4h.game_deals.ui.components.SearchBar
import com.sok4h.game_deals.ui.viewModel.MainViewModel

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


        if (uiState.isLoading) {

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                CircularProgressIndicator(
                    modifier = Modifier
                        .size(25.dp),
                    strokeWidth = 2.dp, color = Color.Red
                )
            }

        }

        if (uiState.gameListState.isNotEmpty()) {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight(0.8f)
            ) {

                items(items = uiState.gameListState,) { gameItem ->

                    GameDealCard(
                        game = gameItem,
                        onAddToWatchList = {
                            viewModel.setStateEvent(MainScreenEvents.AddGametoWatchList(it))

                            Log.e("TAG", "Añadido")
                        },
                        onRemoveFromWatchList = {


                            viewModel.setStateEvent(
                                MainScreenEvents.RemoveFromWatchList(
                                    it
                                )
                            )
                        },
                        onDealPressed = {


                        }
                    )



                }
            }

        }

        if(uiState.gameListErrorMessage.isNotEmpty()){

            Text(text = uiState.gameListErrorMessage)
        }
        if (uiState.dealListState.isNotEmpty()) {

            LazyVerticalGrid(

                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                columns = GridCells.Fixed(2),
                content = {

                    items(items = uiState.dealListState) {
                        DealCard(deal = it)

                    }
                },

                )

        }
        
      
    }


}

