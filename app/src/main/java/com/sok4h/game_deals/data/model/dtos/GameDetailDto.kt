package com.sok4h.game_deals.data.model.dtos


import com.google.gson.annotations.SerializedName

data class GameDetailDto(
    @SerializedName("cheapestPriceEver")
    val cheapestPriceEver: CheapestPriceEver,
    @SerializedName("deals")
    val deals: List<DealDto>,
    @SerializedName("info")
    val info: Info
)

data class Info(
    @SerializedName("steamAppID")
    val steamAppID: String,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("title")
    val title: String?
)

data class CheapestPriceEver(
    @SerializedName("date")
    val date: Int,
    @SerializedName("price")
    val price: String
)