package com.sok4h.game_deals.ui.viewStates

import com.sok4h.game_deals.ui.ui_model.DealDetailModel
import com.sok4h.game_deals.ui.ui_model.GameDetailModel

data class MainScreenState(
    val gameListState: List<GameDetailModel> = emptyList(),
    val dealListState: MutableList<DealDetailModel> = mutableListOf(),
    val isGameLoading: Boolean = false,
    val gameListErrorMessage: String = "",
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val sortDealsBy: String = "Recent",
    val minPrice:String ="0",
    val maxPrice:String="50",
)
