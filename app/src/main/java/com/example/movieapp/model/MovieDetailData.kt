package com.example.movieapp.model

import androidx.room.*
import com.example.movieapp.DB.Converters.CastConverter

@Entity
data class MovieDetailData(
   @PrimaryKey
   val id:Int,
   val title :String,
   val overView:String,
   @TypeConverters(CastConverter::class)
   var cast :List<Cast>,
   val rate:Double,
   val hasVideo:Boolean,
   val backGround:String,
   val poster:String,
   var videoUrlOnYoutube:String ="youtube"

)