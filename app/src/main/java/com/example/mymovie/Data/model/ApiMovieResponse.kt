package com.example.mymovie.data.model

import com.example.mymovie.domain.model.Movie

// data/model/ApiMovieResponse.kt
data class ApiMovieResponse(
    val results: List<ApiMovieDto>
)

data class ApiMovieDto(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val overview: String
) {
    fun toMovieItem(): Movie = Movie(
        id = id,
        title = title,
        poster = poster_path!!,
        overview = overview
    )
}