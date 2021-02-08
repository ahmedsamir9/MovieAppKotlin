package com.example.movieapp.model

import androidx.room.*
import com.example.movieapp.DB.Converters.DataTypeConverter


@Entity
data class MovieDetailData(
   @PrimaryKey
   val id:Int,
   val title :String,
   val overView:String,
   @TypeConverters(DataTypeConverter::class)
   var cast :List<Cast>,
   val rate:Double,
   val hasVideo:Boolean,
   val backGround:String,
   val poster:String,
   var videoUrlOnYoutube:String ="youtube"
)