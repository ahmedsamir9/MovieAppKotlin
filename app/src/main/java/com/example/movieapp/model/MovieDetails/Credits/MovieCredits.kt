package com.example.movieapp.model.MovieDetails.Credits


import com.example.movieapp.model.Cast
import com.google.gson.annotations.SerializedName

data class MovieCredits(
    @SerializedName("cast")
    var cast: List<Cast>?,
    @SerializedName("crew")
    var crew: List<Crew>?,
    @SerializedName("id")
    var movieId: Int?
)