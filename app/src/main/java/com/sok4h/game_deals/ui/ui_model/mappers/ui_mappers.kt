package com.sok4h.game_deals.ui.ui_model.mappers

import com.sok4h.game_deals.data.model.dtos.*
import com.sok4h.game_deals.ui.ui_model.*

fun DealDto.toDealModel(): DealModel {

    val imageUrl = storeID.toInt() - 1
    return DealModel(
        dealID,
        price,
        retailPrice,
        savings,
        storeID,
        storeImage = "https://www.cheapshark.com/img/stores/logos/$imageUrl.png"
    )

}


fun GameDetailDto.toGameDetailModel(id: String,isFavorite:Boolean=false): GameDetailModel {

    return GameDetailModel(
        cheapestPriceEver.toCheapestPriceEverModel(),
        deals.map { it.toDealModel() },
        info.toInfoModel(id,isFavorite),
        deals[0].price

    )
}

fun CheapestPriceEverDto.toCheapestPriceEverModel(): CheapestPriceEverModel {

    return CheapestPriceEverModel(date, price)
}

fun InfoDto.toInfoModel(id: String,isFavorite: Boolean): InfoModel {

    return InfoModel(
        thumb, title, gameId = id, isFavorite = isFavorite
    )
}


fun DealDetailDto.toDealDetailModel(): DealDetailModel {
    val idImage = storeID.toInt() - 1
    return DealDetailModel(
        dealID,
        dealRating,
        gameID,
        isOnSale,
        lastChange,
        normalPrice,
        salePrice,
        savings,
        steamRatingCount,
        steamRatingPercent,
        steamRatingText,
        storeID,
        thumb,
        title,
        storeLogo = "https://www.cheapshark.com/img/stores/icons/$idImage.png"
    )
}
