package com.example.mergeanimalsandflowers.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {

    @TypeConverter
    fun fromStringToCharList(value: String?): List<Char?>? {
        val listType = object : TypeToken<List<Char?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromCharListToString(list: List<Char?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

}