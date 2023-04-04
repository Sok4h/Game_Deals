package com.sok4h.game_deals.utils

import com.sok4h.game_deals.ui.ui_model.DealDetailModel

sealed interface DealState{

    data class Success(val data:List<DealDetailModel>):DealState
    data class Error(val error:Exception): DealState
    object Loading: DealState
}