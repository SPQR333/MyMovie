package com.example.mymovie.Data.api

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page: Int,
    val results: List<ApiMovie>,
    val total_pages: Int,
    val total_results: Int
)

data class ApiMovie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("release_date") val releaseDate: String
)
