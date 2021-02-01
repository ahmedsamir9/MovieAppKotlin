package com.example.movieapp.model.searchmodel


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var results: List<Result>?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("total_results")
    var totalResults: Int?
)