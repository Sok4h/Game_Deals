package com.sok4h.game_deals.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.sok4h.game_deals.data.model.entities.StoreEntity

@Dao
interface StoreDao {

    @Query("SELECT * from stores")
    suspend fun getAllStores(): List<StoreEntity>

    @Query("SELECT * from stores WHERE storeID =:storeID")
    suspend fun getStoreById(storeID: String):StoreEntity

}