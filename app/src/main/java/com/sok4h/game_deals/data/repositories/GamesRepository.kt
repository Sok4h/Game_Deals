package com.sok4h.game_deals.data.repositories

import com.sok4h.game_deals.data.model.dtos.GameDetailDto
import com.sok4h.game_deals.data.model.dtos.GameDto
import com.sok4h.game_deals.data.network.CheapSharkService

class GamesRepository(/*private val service: CheapSharkService*/) : IGamesRepository {

    private val service = CheapSharkService()
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

            return Result.failure(e)
        }
    }
}