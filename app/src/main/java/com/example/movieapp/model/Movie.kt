package com.example.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
@Entity
data class Movie(
    var backdropPath: String?,
    @PrimaryKey
    @Json(name = "id")
    var id: Int?,
    var originalTitle: String?,
    var posterPath: String?,
    var title: String?,
    var category: String?,
    var voteAverage: Double?

    )
