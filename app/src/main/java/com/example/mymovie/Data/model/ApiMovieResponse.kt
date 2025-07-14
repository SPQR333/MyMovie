package com.example.mymovie.Data.model

import com.example.mymovie.Domain.model.MovieModel

// data/model/ApiMovieResponse.kt
data class ApiMovieResponse(
    val results: List<ApiMovieDto>
)

data class ApiMovieDto(
    val id: Int,
    val title: String,
    val poster_path: String?
) {
    fun toMovieItem(): MovieModel = MovieModel(
        id = id,
        title = title,
        poster = poster_path!!
    )
}