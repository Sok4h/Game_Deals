package com.sok4h.game_deals.ui.ui_model

data class DealDetailModel(

    val dealID: String,
    val dealRating: String,
    val gameID: String,
    val isOnSale: String,
    val lastChange: Int,
    val normalPrice: String,
    val salePrice: String,
    val savings: String,
    val numberReviews: String,
    val steamRatingPercent: String?,
    val steamRatingText: String?,
    val storeID: String,
    val gameImage: String,
    val title: String,
    val storeLogo: String,
)
