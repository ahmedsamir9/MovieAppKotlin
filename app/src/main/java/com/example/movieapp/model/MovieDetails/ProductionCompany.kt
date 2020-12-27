package com.example.movieapp.model.MovieDetails


import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("logo_path")
    var logoPath: Any?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("origin_country")
    var originCountry: String?
)