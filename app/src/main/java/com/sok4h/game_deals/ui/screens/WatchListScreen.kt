package com.sok4h.game_deals.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.sok4h.game_deals.events.WatchListScreenEvent
import com.sok4h.game_deals.ui.components.GameDealCard
import com.sok4h.game_deals.utils.GameState


@Composable
fun WatchListScreen(gameState: GameState,onEvent:(WatchListScreenEvent)->Unit) {

    when(gameState){
        is GameState.Error -> {}
        is GameState.Loading -> {

        }
        is GameState.Success -> {

            LazyColumn{

                items(items = gameState.data){game->

                    GameDealCard(
                        game = game,
                        onAddToWatchList = {},
                        onRemoveFromWatchList ={onEvent(WatchListScreenEvent.RemoveFromWatchList(it))} ,
                        onDealPressed ={}
                    )
                }

            }
        }

    }



}