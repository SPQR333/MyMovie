package com.example.mymovie.Domain.usecase

import com.example.mymovie.Domain.MovieItem
import com.example.mymovie.Domain.model.MovieModel
import com.example.mymovie.Domain.repository.MovieRepository
import javax.inject.Inject

class GetListMovieUseCase @Inject constructor(
    private  val repository: MovieRepository) {

    suspend operator fun invoke(): List<MovieModel> {
        return repository.getPopularMovies()
    }
}