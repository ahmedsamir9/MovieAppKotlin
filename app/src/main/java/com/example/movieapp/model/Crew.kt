package com.example.movieapp.model


import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("adult")
    var adult: Boolean?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("credit_id")
    var creditId: String?,
    @SerializedName("department")
    var department: String?,
    @SerializedName("genre_ids")
    var genreIds: List<Int>?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("job")
    var job: String?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("popularity")
    var popularity: Double?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("video")
    var video: Boolean?,
    @SerializedName("vote_average")
    var voteAverage: Double?,
    @SerializedName("vote_count")
    var voteCount: Int?
)