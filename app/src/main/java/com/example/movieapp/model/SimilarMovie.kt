package com.example.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
@Entity
data class SimilarMovie (
    var backdropPath: String?,
    @PrimaryKey(autoGenerate = true)
    val idInDatabase:Int,
    var id: Int?,
    var originalTitle: String?,
    var posterPath: String?,
    var title: String?,
    var similarTo: String?,
    var voteAverage: Double?
)