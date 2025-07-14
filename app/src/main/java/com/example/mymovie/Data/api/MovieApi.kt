package com.example.mymovie.Data.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("trending/movie/day")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZjZlOGRjOThjMzY5YjY5YTFjYjcwNzBkZGQ4MDc2NSIsIm5iZiI6MTc0NTQ0NjIxNS4xMzUsInN1YiI6IjY4MDk2NTQ3YjJiNzIyYWVkZjhhMmE1YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.CaeNCane7d3Mzo5mzd1Ic9mzJWQ3iJRD7J2z8DsF3eQ", // ← Замените это!
        @Query("language") language: String = "en-US",
      //  @Query("page") page: Int = 2
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Query("api_key") apiKey: String = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZjZlOGRjOThjMzY5YjY5YTFjYjcwNzBkZGQ4MDc2NSIsIm5iZiI6MTc0NTQ0NjIxNS4xMzUsInN1YiI6IjY4MDk2NTQ3YjJiNzIyYWVkZjhhMmE1YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.CaeNCane7d3Mzo5mzd1Ic9mzJWQ3iJRD7J2z8DsF3eQ",
        @Query("language") language: String = "en-US"
    ): MoviesResponse
}
