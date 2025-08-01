package com.example.mymovie.Data.remote

import com.example.mymovie.Data.api.ApiMovie
import com.example.mymovie.Domain.model.Movie

class MovieMapper {
        fun mapToDomain(apiMovie: ApiMovie): Movie {
            return Movie(
                id = apiMovie.id,
                title = apiMovie.title,
                poster = apiMovie.posterPath?.let {
                    "https://image.tmdb.org/t/p/w500$it"
                } ?: "",
            )
        }
    }


