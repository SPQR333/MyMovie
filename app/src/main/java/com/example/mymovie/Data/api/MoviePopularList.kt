package com.example.mymovie.Data.api

import com.example.mymovie.Domain.model.Movie
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
    @SerializedName("poster_path") val  posterPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("release_date") val releaseDate: String,
    val overview: String

) {
    fun ApiMovie.toDomain(): Movie = Movie(
        id = this.id,
        title = this.title,
        poster = this.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
    )
}
