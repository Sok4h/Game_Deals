package com.sok4h.game_deals.data.repositories

import android.util.Log
import com.sok4h.game_deals.data.model.dtos.DealDetailDto
import com.sok4h.game_deals.data.network.CheapSharkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DealsRepository : IDealsRepository {

    private val service = CheapSharkService()
    override fun getListOfDeals(
        storeID: String?,
        pageNumber: Int?,
        sortBy: String?,
        desc: Boolean,
        lowerPrice: Int?,
        upperPrice: Int?
    ): Flow<Result<List<DealDetailDto>>> {

        return flow {
            service.getListOfDeals(storeID, pageNumber, sortBy, desc, lowerPrice, upperPrice)
                .catch {
                    Result.failure<Exception>(it)
                }
                .collect { response ->

                    if (response.isSuccessful) emit(Result.success(response.body()!!))
                    else {
                        emit(Result.failure(Exception(response.errorBody().toString())))
                    }
                }

        }
    }

}