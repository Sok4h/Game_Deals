package com.sok4h.game_deals.data.model.dtos


import com.google.gson.annotations.SerializedName

data class GameDetailDto(
    @SerializedName("cheapestPriceEver")
    val cheapestPriceEver: CheapestPriceEverDto,
    @SerializedName("deals")
    val deals: List<DealDto>,
    @SerializedName("info")
    val info: InfoDto
)

data class InfoDto(
    @SerializedName("steamAppID")
    val steamAppID: String,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("title")
    val title: String?
)

data class CheapestPriceEverDto(
    @SerializedName("date")
    val date: Int,
    @SerializedName("price")
    val price: String
)