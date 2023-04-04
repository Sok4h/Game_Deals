package com.sok4h.game_deals.data.database.daos

import androidx.room.*
import com.sok4h.game_deals.data.model.entities.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query("Select * from games")
    fun getAllGames(): Flow<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(gameEntity: GameEntity)

    @Query("DELETE FROM games WHERE gameId =:id")
    fun deleteGame(id:String)

    @Query("SELECT EXISTS(SELECT * FROM games WHERE gameId = :id)")
    fun gameIsFavorite( id:String): Boolean

}