package com.sok4h.game_deals.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stores")
data class StoreEntity(
    @PrimaryKey
    val storeID: String,
    val StoreName:String,
    val isActive :Int,

)