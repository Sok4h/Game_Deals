package com.sok4h.game_deals.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.sok4h.game_deals.ui.components.GameDealCard
import com.sok4h.game_deals.utils.GameState


@Composable
fun WatchListScreen(gameState: GameState) {

    when(gameState){
        is GameState.Error -> TODO()
        is GameState.Loading -> {

        }
        is GameState.Success -> {

            LazyColumn{

                items(items = gameState.data){

                    GameDealCard(
                        game = it,
                        onAddToWatchList = {},
                        onRemoveFromWatchList ={} ,
                        onDealPressed ={}
                    )
                }

            }
        }
    }



}