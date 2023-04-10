package com.sok4h.game_deals.ui.viewStates

import com.sok4h.game_deals.ui.ui_model.GameDetailModel

data class WatchListScreenState(
    val gameListState: List<GameDetailModel> = emptyList(),
    val gameListErrorMessage:String="",
    val isLoading:Boolean=false,
)
