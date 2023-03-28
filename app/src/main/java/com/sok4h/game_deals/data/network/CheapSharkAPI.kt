package com.sok4h.game_deals.data.network

import com.sok4h.game_deals.data.model.dtos.DealDetailDto
import com.sok4h.game_deals.data.model.dtos.GameDetailDto
import com.sok4h.game_deals.data.model.dtos.GameDto
import com.sok4h.game_deals.data.model.dtos.StoresDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CheapSharkAPI {

    //https://www.cheapshark.com/api/1.0/games?title=batman&steamAppID=35140&limit=60&exact=0

    @GET("games")
    suspend fun searchGameDeal(@Query("title") title: String):Response<List<GameDto>>

    @GET("deals")
    suspend fun getListOfDeals(
        @Query("storeID") storeID: String?,
        @Query("pageNumber") pageNumber:Int?,
        @Query("sortBy") sortBy:String?,
        @Query("desc") desc:Boolean?,
        @Query("lowerPrice") lowerPrice:Int?,
        @Query("upperPrice") upperPrice:Int?,
    ):Response<List<DealDetailDto>>

    @GET("games")
    suspend fun getGameById(@Query("id") id:Int):Response<GameDetailDto>

    @GET("stores")
    suspend fun getStoreInfo():Response<List<StoresDto>>



}