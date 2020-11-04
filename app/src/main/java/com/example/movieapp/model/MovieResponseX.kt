package com.example.movieapp.model


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var results: List<MovieShow>?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("total_results")
    var totalResults: Int?
){
    override fun toString(): String {
        return "MovieResponse{" +
                "page = '" + page + '\'' +
                ",total_pages = '" + totalPages + '\'' +
                ",results = '" + results + '\'' +
                ",total_results = '" + totalResults + '\'' +
                "}"
    }
}