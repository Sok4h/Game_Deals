package com.sok4h.game_deals.data.repositories

import com.sok4h.game_deals.data.model.dtos.DealDetailDto
import kotlinx.coroutines.flow.Flow

interface IDealsRepository {

     fun getListOfDeals(
        storeID: String?=null,
        pageNumber: Int?=5,
        sortBy: String?=null,
        desc: Boolean=false,
        lowerPrice: Int?=null,
        upperPrice: Int?=null
    ): Flow<Result<List<DealDetailDto>>>



}