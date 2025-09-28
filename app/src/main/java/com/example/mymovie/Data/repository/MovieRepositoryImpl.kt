package com.example.mymovie.Data.repository

import com.example.mymovie.Data.api.ApiMovie
import com.example.mymovie.Data.api.MovieApi
import com.example.mymovie.Data.remote.MovieMapper
import com.example.mymovie.Domain.model.Movie
import com.example.mymovie.Domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val mapper: MovieMapper, // <- Добавляем маппер

) : MovieRepository {

    override suspend fun getPopularMovies(): List<Movie> = withContext(dispatcher) {
        api.getPopularMovie().results.map { mapper.mapToDomain(it) }
    }



   /* private fun ApiMovie.toDomain(): Movie = Movie(
        id = id,
        title = title,
        poster = posterPath ?:""
    )*/
}