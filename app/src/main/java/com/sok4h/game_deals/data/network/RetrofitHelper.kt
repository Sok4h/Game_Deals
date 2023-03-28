package com.sok4h.game_deals.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    const val BASEURL="https://www.cheapshark.com/api/1.0/"

    fun getRetrofit(): CheapSharkAPI {

        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CheapSharkAPI::class.java)
    }

}