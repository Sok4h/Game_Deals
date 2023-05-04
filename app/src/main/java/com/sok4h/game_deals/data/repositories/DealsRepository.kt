package com.sok4h.game_deals.data.repositories

import com.sok4h.game_deals.data.network.CheapSharkServiceImpl
import com.sok4h.game_deals.ui.ui_model.DealDetailModel
import com.sok4h.game_deals.ui.ui_model.mappers.toDealDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DealsRepository(private val service: CheapSharkServiceImpl) : IDealsRepository {

    override fun getListOfDeals(
        storeID: String?,
        pageNumber: Int?,
        sortBy: String?,
        desc: Boolean?,
        lowerPrice: Int?,
        upperPrice: Int?,
    ): Flow<Result<MutableList<DealDetailModel>>> =

        service.getListOfDeals(storeID, pageNumber, sortBy, desc, lowerPrice, upperPrice)
            .catch {
                Result.failure<Exception>(it)
            }
            .map { response ->
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val deals = response.body()!!.map { it.toDealDetailModel() }.toMutableList()
                        return@map Result.success(deals)
                    } else {
                        // TODO: manejo pro de errores
                        return@map Result.failure(Exception("Cuerpo Vacio"))
                    }
                } else {

                    return@map Result.failure(Exception(response.raw().code.toString()))
                }

            }


}

