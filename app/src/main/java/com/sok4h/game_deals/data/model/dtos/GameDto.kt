package com.sok4h.game_deals.data.model.dtos


import com.google.gson.annotations.SerializedName

data class GameDto(
    @SerializedName("cheapest")
    val cheapest: String,
    @SerializedName("cheapestDealID")
    val cheapestDealID: String,
    @SerializedName("external")
    val title: String,
    @SerializedName("gameID")
    val gameID: String,
    @SerializedName("internalName")
    val internalName: String,
    @SerializedName("thumb")
    val image: String
)