package com.example.mymovie.Data.model

import com.example.mymovie.Domain.model.Movie

// data/model/ApiMovieResponse.kt
data class ApiMovieResponse(
    val results: List<ApiMovieDto>
)

data class ApiMovieDto(
    val id: Int,
    val title: String,
    val poster_path: String?
) {
    fun toMovieItem(): Movie = Movie(
        id = id,
        title = title,
        poster = poster_path!!
    )
}