package com.sok4h.game_deals.data.repositories

import com.sok4h.game_deals.data.network.CheapSharkServiceImpl
import com.sok4h.game_deals.ui.ui_model.DealDetailModel
import com.sok4h.game_deals.ui.ui_model.mappers.toDealDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DealsRepository(private val service: CheapSharkServiceImpl) : IDealsRepository {


    override fun getListOfDeals(
        storeID: String?,
        pageNumber: Int?,
        sortBy: String?,
        desc: Boolean?,
        lowerPrice: Int?,
        upperPrice: Int?,
    ): Flow<Result<MutableList<DealDetailModel>>> {

        return service.getListOfDeals(storeID, pageNumber, sortBy, desc, lowerPrice, upperPrice)
           .map { response ->
                if (response.isSuccessful) {

                    if (response.body() != null) {

                        val deals = response.body()!!.map { it.toDealDetailModel() }.toMutableList()

                        Result.success(deals)
                    } else {

                        Result.failure(Exception(response.message()))
                    }

                } else {

                    Result.failure(Exception(response.raw().code.toString()))
                }

            }

    }

}