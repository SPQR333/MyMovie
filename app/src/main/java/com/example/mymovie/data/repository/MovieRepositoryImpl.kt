package com.example.mymovie.data.repository

import com.example.mymovie.data.api.MovieApi
import com.example.mymovie.domain.model.Movie
import com.example.mymovie.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// data/repository/MovieRepositoryImpl.kt

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mymovie.data.mapper.MovieMapper
import com.example.mymovie.data.mapper.MovieMapper_Factory
import com.example.mymovie.data.remote.MoviePagingSource
import com.example.mymovie.data.remote.SearchPagingSource // Новый PagingSource для поиска
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val mapper: MovieMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {

    // Существующий метод (можно оставить для обратной совместимости)
    override suspend fun getPopularMovies(): List<Movie> = withContext(dispatcher) {
        api.getPopularMovie().results.map { mapper.mapToDomain(it) }
    }

    // Пагинация популярных фильмов
    override fun getPopularMoviesPaging(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(api, mapper) // Используем существующий MoviePagingSource
            }
        ).flow
    }

    // Пагинация поиска - НОВЫЙ МЕТОД
    override fun searchMoviesPaging(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchPagingSource(api, mapper, query) // Новый PagingSource для поиска
            }
        ).flow
    }
}