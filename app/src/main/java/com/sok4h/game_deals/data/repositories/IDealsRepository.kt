package com.sok4h.game_deals.data.repositories

import com.sok4h.game_deals.ui.ui_model.DealDetailModel
import kotlinx.coroutines.flow.Flow

interface IDealsRepository {

     fun getListOfDeals(
        storeID: String?=null,
        pageNumber: Int?=null,
        sortBy: String?=null,
        desc: Boolean=false,
        lowerPrice: Int?=null,
        upperPrice: Int?=null
    ): Flow<Result<MutableList<DealDetailModel>>>



}