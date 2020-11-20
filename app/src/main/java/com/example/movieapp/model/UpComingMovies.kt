package com.example.movieapp.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class UpComingMovies(

    var idInDatabase:Int,
    var backdropPath: String?,
    var id: Int?,
    var originalTitle: String?,
    var posterPath: String?,
    @PrimaryKey
    var title: String,
    var category: String?,
    var voteAverage: Double?

)