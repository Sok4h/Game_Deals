package com.sok4h.game_deals.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sok4h.game_deals.data.model.entities.StoreImagesEntity


class TypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromImage(image:StoreImagesEntity):String{

        return gson.toJson(image)
    }

    @TypeConverter
    fun toImage(image:String):StoreImagesEntity{

        return gson.fromJson(image,StoreImagesEntity::class.java)
    }



}