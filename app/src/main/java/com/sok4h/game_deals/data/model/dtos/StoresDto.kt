package com.sok4h.game_deals.data.model.dtos

import com.google.gson.annotations.SerializedName

data class StoresDto(
    @SerializedName("images")
    val images: StoreImagesDto,
    @SerializedName("isActive")
    val isActive: Int,
    @SerializedName("storeID")
    val storeID: String,
    @SerializedName("storeName")
    val storeName: String
)

data class StoreImagesDto(
    @SerializedName("banner")
    val banner: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("logo")
    val logo: String
)