package com.example.mymovie.Data.repository

import com.example.mymovie.Data.api.ApiMovie
import com.example.mymovie.Data.api.MovieApi
import com.example.mymovie.Domain.model.MovieModel
import com.example.mymovie.Domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {

    override suspend fun getPopularMovies(): List<MovieModel> = withContext(dispatcher) {
        api.getPopularMovie().results.map { it.toDomain() }
    }



    private fun ApiMovie.toDomain(): MovieModel = MovieModel(
        id = id,
        title = title,
        poster = posterPath!!
    )
}