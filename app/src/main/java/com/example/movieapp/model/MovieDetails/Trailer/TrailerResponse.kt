package com.example.movieapp.model.MovieDetails.Trailer


import com.google.gson.annotations.SerializedName

data class TrailerResponse(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("results")
    var trailerResults: List<TrailerResult>?
)