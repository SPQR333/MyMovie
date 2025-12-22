package com.example.mymovie.data.mapper

import com.example.mymovie.data.api.ApiMovie
import com.example.mymovie.domain.model.Movie
import javax.inject.Inject

class  MovieMapper @Inject constructor(){
        fun mapToDomain(apiMovie: ApiMovie): Movie {
            return Movie(
                id = apiMovie.id,
                title = apiMovie.title,
                overview = apiMovie.overview,
                poster = apiMovie.posterPath?.let {
                    "https://image.tmdb.org/t/p/w500$it"
                } ?: "",
            )
        }
    }