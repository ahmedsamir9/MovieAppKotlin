package com.example.movieapp.model


import com.google.gson.annotations.SerializedName

data class MovieShow(
    @SerializedName("adult")
    var adult: Boolean?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("genre_ids")
    var genreIds: List<Int>?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("popularity")
    var popularity: Double?,
    @SerializedName("poster_path")
    var posterPath: String,
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
){
    override fun toString(): String {
        return "ResultsItem{" +
                "overview = '" + overview + '\'' +
                ",original_language = '" + originalLanguage + '\'' +
                ",original_title = '" + originalTitle + '\'' +
                ",video = '" + video + '\'' +
                ",title = '" + title + '\'' +
                ",genre_ids = '" + genreIds + '\'' +
                ",poster_path = '" + posterPath + '\'' +
                ",backdrop_path = '" + backdropPath + '\'' +
                ",release_date = '" + releaseDate + '\'' +
                ",popularity = '" + popularity + '\'' +
                ",vote_average = '" + voteAverage + '\'' +
                ",id = '" + id + '\'' +
                ",adult = '" + adult + '\'' +
                ",vote_count = '" + voteCount + '\'' +
                "}"
    }
}