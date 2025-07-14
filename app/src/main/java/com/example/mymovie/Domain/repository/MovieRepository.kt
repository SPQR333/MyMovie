package com.example.mymovie.Domain.repository

import com.example.mymovie.Domain.MovieItem
import com.example.mymovie.Domain.model.MovieModel

interface MovieRepository {
    suspend fun getPopularMovies(): List<MovieModel>
}