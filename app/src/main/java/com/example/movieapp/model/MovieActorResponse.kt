package com.example.movieapp.model


import com.google.gson.annotations.SerializedName

data class MovieActorResponse(
    @SerializedName("cast")
    var cast: List<CastX>?,
    @SerializedName("crew")
    var crew: List<Crew>?,
    @SerializedName("id")
    var id: Int?
)