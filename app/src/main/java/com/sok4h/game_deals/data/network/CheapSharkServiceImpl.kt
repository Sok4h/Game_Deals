package com.sok4h.game_deals.data.network

import android.util.Log
import com.sok4h.game_deals.data.model.dtos.GameDetailDto
import com.sok4h.game_deals.data.model.dtos.GameDto
import com.sok4h.game_deals.data.model.dtos.StoresDto
import kotlinx.coroutines.flow.flow
import retrofit2.Response


class CheapSharkServiceImpl(private val api: CheapSharkAPI) {


    suspend fun searchGame(title: String): Response<List<GameDto>> {

        return api.searchGameDeal(title)

    }

    fun getListOfDeals(
        storeID: String?,
        pageNumber: Int?=0,
        sortBy: String?,
        desc: Boolean?=false,
        lowerPrice: Int?,
        upperPrice: Int?,
    ) = flow {

        val result = api.getListOfDeals(storeID, pageNumber, sortBy, desc, lowerPrice, upperPrice)
        emit(result)
        Log.e("Deals", result.toString())

    }

    suspend fun getGameById(id: String): Response<GameDetailDto> {

        return api.getGameById(id)
    }

    suspend fun getStoreInfo(): Response<List<StoresDto>> {

        return api.getStoreInfo()
    }

    suspend fun getMultipleGames(ids: String): Response<List<GameDetailDto>> {

        return api.getMultipleGames(ids)
    }


}