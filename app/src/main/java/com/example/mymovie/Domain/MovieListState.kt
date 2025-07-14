package com.example.mymovie.Domain

// presentation/model/MovieListState.kt
data class MovieListState(
    val movies: List<MovieUi>,  // Фильмы с UI-специфичными полями
    val genres: List<MovieItem.Genre>,
    val searchQuery: String,  // Текст из поиска
    val selectedGenreId: Int?  // ID выбранного жанра (null = все жанры)
) {
    val filteredMovies: List<MovieUi>
        get() = movies
            .filter { movie ->
                searchQuery.isEmpty() ||
                        movie.title.contains(searchQuery, ignoreCase = true)
            }
           /* .filter { movie ->
                selectedGenreId == null ||
                      //  movie.genreIds.contains(selectedGenreId)
            }*/
}

data class MovieUi(
    val id: Int,
    val title: String,
    val posterUrl: String,  // Полный URL (преобразованный из posterPath)
    val formattedRating: String,  // "★ 8.4"
    val releaseYear: String  // "2023"
)