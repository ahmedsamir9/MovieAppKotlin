package com.example.movieapp.model.searchmodel.people


import com.google.gson.annotations.SerializedName

data class PeopleSearchResponse(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var results: List<People>?,
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("total_results")
    var totalResults: Int?
)