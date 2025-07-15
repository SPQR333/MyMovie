package com.example.mymovie.Domain.repository

import com.example.mymovie.Domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(): List<Movie>
}