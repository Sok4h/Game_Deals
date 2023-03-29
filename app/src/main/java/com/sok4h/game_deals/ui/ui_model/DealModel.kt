package com.sok4h.game_deals.ui.ui_model

import com.google.gson.annotations.SerializedName

data class DealModel(

    val dealID: String,
    val price: String,
    val retailPrice: String,
    val savings: String,
    val storeID: String,
    val storeImage:String
)
