package com.sok4h.game_deals.ui.ui_model.mappers

import com.sok4h.game_deals.data.model.dtos.CheapestPriceEverDto
import com.sok4h.game_deals.data.model.dtos.DealDto
import com.sok4h.game_deals.data.model.dtos.GameDetailDto
import com.sok4h.game_deals.data.model.dtos.InfoDto
import com.sok4h.game_deals.ui.ui_model.CheapestPriceEverModel
import com.sok4h.game_deals.ui.ui_model.DealModel
import com.sok4h.game_deals.ui.ui_model.GameDetailModel
import com.sok4h.game_deals.ui.ui_model.InfoModel

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


fun GameDetailDto.toGameDetailModel(id:String): GameDetailModel {

    return GameDetailModel(
        cheapestPriceEver.toCheapestPriceEverModel(),
        deals.map { it.toDealModel() },
        info.toInfoModel(id)
    )
}

fun CheapestPriceEverDto.toCheapestPriceEverModel(): CheapestPriceEverModel {

    return CheapestPriceEverModel(date, price)
}

fun InfoDto.toInfoModel(id:String): InfoModel {

    return InfoModel(
        thumb, title, gameId = id
    )
}

