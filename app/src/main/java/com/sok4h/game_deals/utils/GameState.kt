package com.sok4h.game_deals.utils

import com.sok4h.game_deals.ui.ui_model.GameDetailModel

sealed interface GameState{

    data class Success(val data:List<GameDetailModel>):GameState
    data class Error(val error:Exception): GameState
    object Loading: GameState

}
