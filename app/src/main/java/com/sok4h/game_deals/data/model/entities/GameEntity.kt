package com.sok4h.game_deals.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("games")
data class GameEntity(
    @PrimaryKey
    val name: String,
    val gameId: String,
    val bestDealId: String,
    val bestPrice : String,
    val image :String,
)
