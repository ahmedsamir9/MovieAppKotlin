package com.example.movieapp.DB.Converters

import androidx.room.TypeConverter
import com.example.movieapp.model.Cast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CastConverter {
    @TypeConverter
    fun fromCastList(cast: List<Cast>?): String? {
        if (cast == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Cast>?>() {}.type
        return gson.toJson(cast, type)
    }
    @TypeConverter
    fun toCastList(castString: String?): List<Cast>? {
        if (castString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Cast>?>() {}.type
        return gson.fromJson<List<Cast>>(castString, type)
    }
}