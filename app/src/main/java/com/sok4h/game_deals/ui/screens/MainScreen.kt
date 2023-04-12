package com.sok4h.game_deals.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sok4h.game_deals.ui.components.FilterDealsBottomSheet
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
            /*  LazyVerticalGrid(
                  modifier = Modifier,
                  horizontalArrangement = Arrangement.spacedBy(8.dp),
                  verticalArrangement = Arrangement.spacedBy(8.dp),
                  columns = GridCells.Fixed(2),
                  content = {

                      items(items = state.dealListState) {deal->

                          DealCard(deal = deal, onDealPressed = {onDealPressed(it)})

                      }
                  },

                  )*/



            DealScreen()

            /*  LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                  items(items = state.dealListState) { deal ->
                      DealCard(deal = deal, onDealPressed = onDealPressed)

                  }
              }*/

        }


    }


}

@ExperimentalMaterial3Api
@Composable
fun DealScreen() {

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState(true)

    Column() {

        IconButton(onClick = { openBottomSheet = !openBottomSheet }, modifier = Modifier.align(Alignment.End) ) {
            Icon(imageVector = Icons.Default.Tune, contentDescription = "")
        }

        if (openBottomSheet) {

            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
                modifier = Modifier.wrapContentHeight()
            ) {

                FilterDealsBottomSheet()
            }
        }
    }
}





