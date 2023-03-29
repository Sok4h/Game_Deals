package com.sok4h.game_deals.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sok4h.game_deals.data.database.daos.GameDao
import com.sok4h.game_deals.data.database.daos.StoreDao
import com.sok4h.game_deals.data.model.entities.GameEntity
import com.sok4h.game_deals.data.model.entities.StoreEntity

@Database(
    entities = [GameEntity::class, StoreEntity::class],
    version = 1
)
@TypeConverters(TypeConverter::class)


abstract class GameDealsDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    abstract fun storeDao(): StoreDao

}