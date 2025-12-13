// domain/usecase/GetPopularMoviesUseCase.kt
package com.example.mymovie.domain.usecase

import androidx.paging.PagingData
import com.example.mymovie.domain.model.Movie
import com.example.mymovie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return repository.getPopularMoviesPaging()
    }
}