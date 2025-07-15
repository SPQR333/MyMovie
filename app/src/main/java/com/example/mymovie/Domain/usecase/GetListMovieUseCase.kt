package com.example.mymovie.Domain.usecase

import com.example.mymovie.Domain.model.Movie
import com.example.mymovie.Domain.repository.MovieRepository
import javax.inject.Inject

class GetListMovieUseCase @Inject constructor(
    private  val repository: MovieRepository) {

    suspend operator fun invoke(): List<Movie> {
        return repository.getPopularMovies()
    }
}