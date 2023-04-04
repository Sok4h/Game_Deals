package com.sok4h.game_deals.ui.viewStates

import com.sok4h.game_deals.utils.DealState
import com.sok4h.game_deals.utils.GameState

data class MainScreenState(
    val gameListState: GameState,
    val dealListState: DealState,
    val isLoading:Boolean,
    val searchQuery:String=""
)
