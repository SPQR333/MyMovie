package com.example.mymovie.Domain

data class MovieUiState(
    val header: MovieHeader,  // Верхний блок (постер, название, рейтинг)
    val description: String,  // Описание фильма
    val comments: List<Comment>  // Комментарии (из другого источника)
) {
    data class MovieHeader(
        val posterUrl: String,
        val title: String,
        val rating: String,  // "8.4/10"
        val releaseYear: String  // "1999"
    )

    data class Comment(
        val author: String,
        val text: String
    )
}