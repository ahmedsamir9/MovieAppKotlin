package com.example.movieapp.model.searchmodel


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("adult")
    var adult: Boolean?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("first_air_date")
    var firstAirDate: String?,
    @SerializedName("gender")
    var gender: Int?,
    @SerializedName("genre_ids")
    var genreIds: List<Int>?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("known_for")
    var knownFor: List<KnownFor>?,
    @SerializedName("known_for_department")
    var knownForDepartment: String?,
    @SerializedName("media_type")
    var mediaType: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("origin_country")
    var originCountry: List<String>?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("original_name")
    var originalName: String?,
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("popularity")
    var popularity: Double?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("profile_path")
    var profilePath: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("video")
    var video: Boolean?,
    @SerializedName("vote_average")
    var voteAverage: Int?,
    @SerializedName("vote_count")
    var voteCount: Int?
)