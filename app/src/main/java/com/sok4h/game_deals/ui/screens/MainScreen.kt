package com.sok4h.game_deals.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
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
    onNavToRecentDeal: () -> Unit,
) {

    var activeBar by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {


        androidx.compose.material3.SearchBar(
            query = state.searchQuery,
            onQueryChange = { onQueryChanged(it) },
            onSearch = { onGameSearch() },
            active = activeBar,
            placeholder = { Text(text = "Search Game") },
            leadingIcon = {
                if (activeBar) {
                    IconButton(onClick = { activeBar = false }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                }
            },
            onActiveChange = { activeBar = !activeBar }
        ) {

            Column(
                Modifier
                    /* .verticalScroll(state = scrollState)*/
                    .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally

            ) {
                if (state.gameListState.isNotEmpty()) {

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxHeight()
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

                if (state.isGameLoading) {

                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(25.dp), strokeWidth = 2.dp,
                        )
                    }

                }

                if (state.gameListErrorMessage.isNotEmpty()) {
                    Text(text = state.gameListErrorMessage)
                }
            }
        }
    }
    /*    val scrollState = rememberScrollState()*/
    Column(
        Modifier
            .fillMaxWidth()
            /* .verticalScroll(state = scrollState)*/
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally

    ) {

        /* SearchBar(
             textValue = state.searchQuery,
             onQueryChanged = { onQueryChanged(it) },
             onSearch = { onGameSearch() },
             modifier = Modifier
                 .fillMaxWidth(0.95f)
                 .padding(bottom = 16.dp)
                 .clip(shape = RoundedCornerShape(8.dp))
         )*/


        /*        if (state.dealListState.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.End),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Recent Deals")

                        IconButton(onClick = onNavToRecentDeal) {
                            Icon(imageVector = Icons.Default.ChevronRight, "")
                        }
                    }



                    LazyRow(verticalAlignment = Alignment.CenterVertically) {
                        items(items = state.dealListState) {

                            DealCard(
                                modifier = Modifier.width((LocalConfiguration.current.screenWidthDp.dp) / 2),
                                deal = it,
                                onDealPressed = { onDealPressed(it) })
                        }

                    }


                }*/

        if (state.isLoading) {

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                CircularProgressIndicator(
                    modifier = Modifier.size(25.dp), strokeWidth = 2.dp,
                )
            }
        }


    }
}









