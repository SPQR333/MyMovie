package com.example.mymovie.Domain

import java.sql.Date

data class MovieItem(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val rating: Double,
    val releaseDate: String,
    val overview: String,
    val genres: List<Genre>,
    val runtime: Int?,
    val genreIds: List<Int>  // Для фильтрации

) {
    data class Genre(val name: String)
}