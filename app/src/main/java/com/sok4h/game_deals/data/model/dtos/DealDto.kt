package com.sok4h.game_deals.data.model.dtos


import com.google.gson.annotations.SerializedName

data class DealDto(
    @SerializedName("dealID")
    val dealID: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("retailPrice")
    val retailPrice: String,
    @SerializedName("savings")
    val savings: String,
    @SerializedName("storeID")
    val storeID: String
)