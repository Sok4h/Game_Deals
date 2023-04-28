package com.sok4h.game_deals.data.repositories

import android.util.Log
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
        desc: Boolean,
        lowerPrice: Int?,
        upperPrice: Int?,
    ): Flow<Result<MutableList<DealDetailModel>>> {

        return service.getListOfDeals(storeID, pageNumber, sortBy, desc, lowerPrice, upperPrice)
            .map { response ->

                if (response.isSuccessful) {
                    Log.e("Exitoso", response.body().toString() )
                    if (response.body() != null) {
                        var deals: MutableList<DealDetailModel> = mutableListOf()
                        try {
                            deals = response.body()!!.map { it.toDealDetailModel() }.toMutableList()
                            Log.e("Tiene cuerpo", deals.toString() )

                        }
                        catch (exception:Exception){
                            Log.e("TAG", exception.toString() )
                        }
                        return@map Result.success(deals)


                    } else {

                        Log.e("TAG","vacio" )
                        return@map Result.failure(Exception("Cuerpo Vacio"))
                    }
                } else {

                    return@map  Result.failure(Exception(response.raw().code.toString()))
                }

            }


    }

}