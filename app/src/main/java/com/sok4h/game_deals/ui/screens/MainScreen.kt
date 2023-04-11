package com.sok4h.game_deals.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sok4h.game_deals.ui.components.DealCard
import com.sok4h.game_deals.ui.components.GameDealCard
import com.sok4h.game_deals.ui.components.SearchBar
import com.sok4h.game_deals.ui.ui_model.GameDetailModel
import com.sok4h.game_deals.ui.viewStates.MainScreenState

@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    state: MainScreenState,
    onQueryChanged: (String) -> Unit,
    onGameSearch: () -> Unit,
    onGameAddedToWatchList: (game: GameDetailModel) -> Unit,
    onGameRemovedWatchList: (id: String) -> Unit,
    onDealPressed: (link: String) -> Unit,
) {

    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
           /* .verticalScroll(state = scrollState)*/
    ) {

        SearchBar(
            textValue = state.searchQuery,
            onQueryChanged = { onQueryChanged(it) },
            onSearch = { onGameSearch() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )


        if (state.isLoading) {

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                CircularProgressIndicator(
                    modifier = Modifier
                        .size(25.dp),
                    strokeWidth = 2.dp, color = Color.Red
                )
            }

        }

        if (state.gameListState.isNotEmpty()) {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight(0.8f)
            ) {

                items(items = state.gameListState) { gameItem ->

                    GameDealCard(
                        game = gameItem,
                        onAddToWatchList = {
                            onGameAddedToWatchList(it)
                        },
                        onRemoveFromWatchList = {
                            onGameRemovedWatchList(
                                it
                            )
                        },
                        onDealPressed = {

                            onDealPressed(it)
                        }
                    )


                }
            }

        }

        if (state.gameListErrorMessage.isNotEmpty()) {

            Text(text = state.gameListErrorMessage)
        }
        if (state.dealListState.isNotEmpty()) {

            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                columns = GridCells.Fixed(2),
                content = {

                    items(items = state.dealListState) {
                        DealCard(deal = it)

                    }
                },

                )

        }


    }


}

