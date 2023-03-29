package com.sok4h.game_deals.data.model.data_mappers

import com.sok4h.game_deals.data.model.dtos.GameDetailDto
import com.sok4h.game_deals.data.model.dtos.StoreImagesDto
import com.sok4h.game_deals.data.model.dtos.StoresDto
import com.sok4h.game_deals.data.model.entities.GameEntity
import com.sok4h.game_deals.data.model.entities.StoreEntity
import com.sok4h.game_deals.data.model.entities.StoreImagesEntity

fun GameDetailDto.toGameEntity(): GameEntity {

    return GameEntity(
        name = info.title!!,
        bestDealId = deals[0].dealID,
        image = info.thumb,
        bestPrice = deals[0].price
    )
}

fun StoresDto.toStoreEntity(): StoreEntity {

    return StoreEntity(storeID, storeName, isActive, images.toStoreImagesEntity())
}

fun StoreImagesDto.toStoreImagesEntity(): StoreImagesEntity {

    return StoreImagesEntity(banner, logo, icon)
}