package com.sok4h.game_deals.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.sok4h.game_deals.ui.components.DealCard
import com.sok4h.game_deals.ui.components.FilterDeals
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
    onSortChanged: (String) -> Unit,
    onMaxPriceChanged: (String) -> Unit,
    onMinPriceChanged: (String) -> Unit,
    onFilterChanged: () -> Unit,

    ) {


    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
        /* .verticalScroll(state = scrollState)*/
    ) {


        SearchBar(
            textValue = state.searchQuery,
            onQueryChanged = { onQueryChanged(it) },
            onSearch = { onGameSearch() },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(bottom = 16.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )


        if (state.isLoading) {

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                CircularProgressIndicator(
                    modifier = Modifier.size(25.dp), strokeWidth = 2.dp, color = Color.Red
                )
            }

        }

        if (state.gameListState.isNotEmpty()) {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight(0.8f)
            ) {

                items(items = state.gameListState) { gameItem ->

                    GameDealCard(game = gameItem, onAddToWatchList = {
                        onGameAddedToWatchList(it)
                    }, onRemoveFromWatchList = {
                        onGameRemovedWatchList(
                            it
                        )
                    }, onDealPressed = {

                        onDealPressed(it)
                    })


                }
            }

        }

        if (state.gameListErrorMessage.isNotEmpty()) {

            Text(text = state.gameListErrorMessage)
        }
        if (state.dealListState.isNotEmpty()) {

            DealScreen(
                state.sortDealsBy,
                onSortChanged = onSortChanged,
                onMaxPriceChanged = onMaxPriceChanged,
                onMinPriceChanged = onMinPriceChanged,
                minPrice = state.minPrice,
                maxPrice = state.maxPrice,
                onFilterChanged = onFilterChanged
            )
            LazyVerticalGrid(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                columns = GridCells.Fixed(2),
                content = {

                    items(items = state.dealListState) { deal ->

                        DealCard(deal = deal, onDealPressed = { onDealPressed(it) })

                    }
                },

                )


            /*    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    items(items = state.dealListState) { deal ->
                        DealCard(deal = deal, onDealPressed = {})

                    }
                }*/


        }


    }


}

@ExperimentalMaterial3Api
@Composable
fun DealScreen(
    sortBy: String,
    minPrice: String,
    maxPrice: String,
    onMinPriceChanged: (String) -> Unit,
    onMaxPriceChanged: (String) -> Unit,
    onSortChanged: (String) -> Unit,
    onFilterChanged: () -> Unit,
) {


    // TODO: Revisar recomposicion para saber si puedo mandar el estado
    var openFilterDialog by rememberSaveable { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth()) {

        IconButton(
            onClick = { openFilterDialog = !openFilterDialog },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(imageVector = Icons.Default.FilterList, contentDescription = "")
        }

        if (openFilterDialog) {

            AlertDialog(properties = DialogProperties(usePlatformDefaultWidth = false),
                onDismissRequest = { openFilterDialog = false }) {

                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    FilterDeals(
                        sortValue = sortBy,
                        onSortChanged = onSortChanged,
                        minPrice = minPrice,
                        maxPrice = maxPrice,
                        onMaxPriceChanged = onMaxPriceChanged,
                        onMinPriceChanged = onMinPriceChanged,
                        onFilterChanged = {
                            onFilterChanged()

                            openFilterDialog = false
                        },
                    )

                }
            }
        }
    }
}





