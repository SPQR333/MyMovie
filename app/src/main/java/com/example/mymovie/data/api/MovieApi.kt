package com.example.mymovie.data.api

import com.example.mymovie.data.model.ApiMovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("trending/movie/day")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String = "6f6e8dc98c369b69a1cb7070ddd80765",
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1,


    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Query("api_key") apiKey: String = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZjZlOGRjOThjMzY5YjY5YTFjYjcwNzBkZGQ4MDc2NSIsIm5iZiI6MTc0NTQ0NjIxNS4xMzUsInN1YiI6IjY4MDk2NTQ3YjJiNzIyYWVkZjhhMmE1YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.CaeNCane7d3Mzo5mzd1Ic9mzJWQ3iJRD7J2z8DsF3eQ",
        @Query("language") language: String = "en-US",

    ): MoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "ru-RU",

    ): MoviesResponse
}

