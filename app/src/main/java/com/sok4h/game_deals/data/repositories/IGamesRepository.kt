package com.sok4h.game_deals.data.repositories

import com.sok4h.game_deals.data.model.dtos.GameDetailDto
import com.sok4h.game_deals.data.model.dtos.GameDto

interface IGamesRepository {

    suspend fun getGameById(id:Int) : Result<GameDetailDto>
    suspend fun searchGameByName(name:String) : Result<List<GameDto>>
}