package com.example.mymovie.domain.repository

import androidx.paging.PagingData
import com.example.mymovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(): List<Movie>

    fun getPopularMoviesPaging(): Flow<PagingData<Movie>>
    fun searchMoviesPaging(query: String): Flow<PagingData<Movie>>
}