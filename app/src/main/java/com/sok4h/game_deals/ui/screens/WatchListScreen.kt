package com.sok4h.game_deals.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sok4h.game_deals.events.WatchListScreenEvent
import com.sok4h.game_deals.ui.components.GameDealCard
import com.sok4h.game_deals.ui.viewStates.WatchListScreenState


@Composable
fun WatchListScreen(state: WatchListScreenState, onEvent: (WatchListScreenEvent) -> Unit) {


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

            items(items = state.gameListState) { game ->

                GameDealCard(
                    game = game,
                    onAddToWatchList = {},
                    onRemoveFromWatchList = {
                        Log.e("TAG", "Eliminado desde card")
                        onEvent(WatchListScreenEvent.RemoveFromWatchList(it))
                    },
                    onDealPressed = {}
                )
            }

        }

    } else {

        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "No tienes juegos en favoritos", style = MaterialTheme.typography.headlineMedium,textAlign = TextAlign.Center, modifier = Modifier.padding(bottom =16.dp))
            Text(text = "Añade juegos para recibir notificaciones cada vez que surja una oferta 😁", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
        }

    }

    // TODO: add error case 


}