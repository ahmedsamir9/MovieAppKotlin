package com.example.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.movieapp.DB.Converters.DataTypeConverter

@Entity
data class ActorData (val name :String?,
                      @PrimaryKey
                      val id:Int
                      ,val biography:String?,
                      val poster:String?,
                      @TypeConverters(DataTypeConverter::class)
                      val movies:List<ActorMovies>)