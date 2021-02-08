package com.example.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class PlayingMovies (

    var backdropPath: String?,
    var id: Int?,
    var originalTitle: String?,
    var posterPath: String?,
    @PrimaryKey
    var title: String,
    var category: String?,
    var voteAverage: Double?

)
