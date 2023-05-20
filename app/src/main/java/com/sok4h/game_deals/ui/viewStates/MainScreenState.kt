package com.sok4h.game_deals.ui.viewStates

import com.sok4h.game_deals.R
import com.sok4h.game_deals.ui.ui_model.DealDetailModel
import com.sok4h.game_deals.ui.ui_model.GameDetailModel

data class MainScreenState(
    val gameListState: List<GameDetailModel> = emptyList(),
    val watchListState: List<GameDetailModel> = emptyList(),
    val watchListErrorMessage:String="",
    val isWatchlistLoading:Boolean=false,
    val dealListState: MutableList<DealDetailModel> = mutableListOf(),
    val isGameLoading: Boolean = false,
    val gameListErrorMessage: String = "",
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val sortDealsBy: String = "Deal Rating",
    val sortDealsById: Int = R.string.deals_filter_rating,
    val minPrice:String ="0",
    val maxPrice:String="50",
)
