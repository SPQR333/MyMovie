package com.example.mymovie.domain.usecase

import com.example.mymovie.domain.model.Movie
import com.example.mymovie.domain.repository.MovieRepository
import javax.inject.Inject

class GetListMovieUseCase @Inject constructor(
    private  val repository: MovieRepository) {

    suspend operator fun invoke(): List<Movie> {
        return repository.getPopularMovies()
    }
}