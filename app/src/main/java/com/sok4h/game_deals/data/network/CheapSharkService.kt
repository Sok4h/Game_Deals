package com.sok4h.game_deals.data.network

import com.sok4h.game_deals.data.model.dtos.GameDetailDto
import com.sok4h.game_deals.data.model.dtos.GameDto
import com.sok4h.game_deals.data.model.dtos.StoresDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import retrofit2.Response


class CheapSharkService() {

    private val api = RetrofitHelper.getRetrofit()

    suspend fun searchGame(title: String): Response<List<GameDto>> {

        return api.searchGameDeal(title)
    }

    fun getListOfDeals(
        storeID: String?,
        pageNumber: Int?,
        sortBy: String?,
        desc: Boolean?,
        lowerPrice: Int?,
        upperPrice: Int?
    ) = flow {

        val result = api.getListOfDeals(storeID, pageNumber, sortBy, desc, lowerPrice, upperPrice)
        emit(result)

    }

    suspend fun getGameById(id: Int): Response<GameDetailDto> {

        return api.getGameById(id)
    }

    suspend fun getStoreInfo(): Response<List<StoresDto>> {

        return api.getStoreInfo()
    }

    suspend fun getMultipleGames(ids:String):Response<List<GameDetailDto>>{

        return api.getMultipleGames(ids)
    }


}