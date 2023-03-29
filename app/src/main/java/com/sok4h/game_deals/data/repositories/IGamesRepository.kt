package com.sok4h.game_deals.data.repositories

import com.sok4h.game_deals.data.model.dtos.GameDetailDto
import com.sok4h.game_deals.data.model.dtos.GameDto
import kotlinx.coroutines.flow.Flow

interface IGamesRepository {

    suspend  fun getGameById(id:Int) : Result<GameDetailDto>
    suspend fun searchGameByName(name:String) : Result<List<GameDto>>

    suspend fun getMultipleGames(ids:String):Result<List<GameDetailDto>>

}