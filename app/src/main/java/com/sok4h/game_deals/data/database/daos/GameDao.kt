package com.sok4h.game_deals.data.database.daos

import androidx.room.*
import com.sok4h.game_deals.data.model.entities.GameEntity

@Dao
interface GameDao {

    @Query("Select * from games")
    suspend fun  getAllGames():List<GameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(gameEntity: GameEntity)

    @Delete()
    fun deleteGame(game:GameEntity)


    @Query("SELECT EXISTS(SELECT * FROM games WHERE id = :id)")
    fun gameIsFavorite(): Boolean

}