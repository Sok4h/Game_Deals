package com.sok4h.game_deals.data.repositories

import android.util.Log
import com.sok4h.game_deals.data.model.data_mappers.toGameEntity
import com.sok4h.game_deals.data.model.dtos.GameDetailDto
import com.sok4h.game_deals.data.model.dtos.GameDto
import com.sok4h.game_deals.data.network.CheapSharkService
import com.sok4h.game_deals.ui.ui_model.GameDetailModel
import com.sok4h.game_deals.ui.ui_model.mappers.toGameDetailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GamesRepository(/*private val service: CheapSharkService*/) : IGamesRepository {

    private val service = CheapSharkService()


    //maybe no es necesario, todo esto lo trae get gamedeals
    override suspend fun getGameById(id: Int): Result<GameDetailDto> {

        try {

            val response = service.getGameById(id)
            return if (response.isSuccessful) {

                val result = response.body()

                if (result!!.info.title.isNullOrEmpty()) {
                    Result.failure(Exception("not a valid id"))
                } else {

                    Result.success(result)

                }

            } else {

                Result.failure(Exception(response.errorBody().toString()))
            }
        } catch (e: Exception) {

            return Result.failure(e)
        }

    }


    override suspend fun searchGameByName(name: String): Result<List<GameDto>> {
        try {

            val response = service.searchGame(name)

            return if (response.isSuccessful) {

                val result = response.body()
                Result.success(result!!)


            } else {

                Result.failure(Exception(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Log.e("TAG", e.message.toString())
            return Result.failure(e)
        }
    }

    override suspend fun getMultipleGames(ids: String): Result<List<GameDetailDto>> {

        try {

            val response = service.getMultipleGames(ids)

            return if (response.isSuccessful) {

                val result = response.body()
                if (result!!.isEmpty()) {
                    return Result.failure(Exception("Lista vacia"))

                }

                Result.success(result)


            } else {

                Result.failure(Exception("Something went Wrong ${response.raw().code()}"))
            }
        } catch (e: Exception) {

            return Result.failure(e)
        }
    }

    suspend fun getGameDeals(name: String): Result<List<GameDetailModel>> {


        return withContext(Dispatchers.IO) {

            try {
                val resultName = searchGameByName(name)
                var ids = ""

                return@withContext if (resultName.isSuccess) {

                    val gameList = resultName.getOrDefault(emptyList())
                    if (gameList.isEmpty()) Result.failure<Exception>(Exception("no se encontró ningun juego"))

                    gameList.forEachIndexed { index, game ->
                        ids += if (index == gameList.size - 1) game.gameID
                        else {

                            game.gameID + ","
                        }
                    }

                    val gamesModel = getMultipleGames(ids)

                    if (gamesModel.isSuccess) {
                        val result = gamesModel.getOrDefault(emptyList())


                        Result.success(result.map {
                            val gameId = gameList.find { gameWithId ->
                                gameWithId.title.contentEquals(it.info.title)
                            }
                            it.toGameDetailModel(gameId!!.gameID)
                        })

                    } else {

                        Result.failure(gamesModel.exceptionOrNull()!!)
                    }
                } else {

                    Result.failure(resultName.exceptionOrNull()!!)
                }
            } catch (e: Exception) {

                return@withContext Result.failure(e)
            }

        }


    }

    override suspend fun checkIfGameIsFavorite(id: String): Boolean {

        // TODO: Añadir dao y buscar si existe
        return true

    }

    override suspend fun saveGametoFavorites(game: GameDetailModel) {

        game.toGameEntity()
    }


}
