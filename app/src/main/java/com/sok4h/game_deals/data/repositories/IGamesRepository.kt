package com.sok4h.game_deals.data.repositories

import com.sok4h.game_deals.data.model.dtos.GameDetailDto
import com.sok4h.game_deals.data.model.dtos.GameDto
import com.sok4h.game_deals.data.model.entities.GameEntity
import com.sok4h.game_deals.ui.ui_model.GameDetailModel
import kotlinx.coroutines.flow.Flow

interface IGamesRepository {

    suspend fun getGameById(id:Int) : Result<GameDetailDto>
    suspend fun searchGameByName(name:String) : Result<List<GameDto>>

    suspend fun getMultipleGames(ids:String):Result<List<GameDetailDto>>

    suspend fun checkIfGameIsFavorite(id: String):Boolean

    suspend fun addGametoWatchList(game:GameDetailModel)

    suspend fun getGameDeals (name:String):Result<List<GameDetailModel>>

    suspend fun getGamesfromDatabase(): Flow<List<GameEntity>>

    suspend fun removeGamefromWatchlist(id:String)




}