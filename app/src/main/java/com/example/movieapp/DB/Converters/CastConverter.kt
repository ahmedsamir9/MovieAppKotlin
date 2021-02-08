package com.example.movieapp.DB.Converters

import androidx.room.TypeConverter
import com.example.movieapp.model.ActorData
import com.example.movieapp.model.ActorMovies
import com.example.movieapp.model.Cast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DataTypeConverter {
    @TypeConverter
    fun fromCastList(cast: List<Cast>) = fromModule(cast)
    @TypeConverter
    fun toCastList(castString: String) =toModule<List<Cast>>(castString)
    @TypeConverter
    fun fromActorMoviesList(movies: List<ActorMovies>) = fromModule(movies)
    @TypeConverter
    fun toActorMoviesList(moviesString: String) =toModule<List<ActorMovies>>(moviesString)
    private inline fun <reified T> fromModule(obj: T?): String? {
        if (obj == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<T?>() {}.type
        return gson.toJson(obj, type)
    }

    private inline fun <reified T> toModule(objString: String?): T? {
        if (objString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<T?>() {}.type
        return gson.fromJson(objString, type)
    }
}