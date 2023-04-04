package com.sok4h.game_deals.ui.ui_model

data class GameDetailModel(
    val cheapestPriceEver: CheapestPriceEverModel,
    val deals: List<DealModel>,
    val info: InfoModel,
    val bestPrice:String
)

data class InfoModel(
    val image: String,
    val title: String?,
    var isFavorite:Boolean=false,
    val gameId:String
)

data class CheapestPriceEverModel(
    val date: Int,
    val price: String
)
