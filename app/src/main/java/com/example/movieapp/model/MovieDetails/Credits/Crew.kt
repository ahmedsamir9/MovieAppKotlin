package com.example.movieapp.model.MovieDetails.Credits


import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("adult")
    var adult: Boolean?,
    @SerializedName("credit_id")
    var creditId: String?,
    @SerializedName("department")
    var department: String?,
    @SerializedName("gender")
    var gender: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("job")
    var job: String?,
    @SerializedName("known_for_department")
    var knownForDepartment: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("original_name")
    var originalName: String?,
    @SerializedName("popularity")
    var popularity: Double?,
    @SerializedName("profile_path")
    var profilePath: String?
)