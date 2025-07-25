package com.example.CurrentMovie.Domain.model

import com.google.gson.annotations.SerializedName

data class CurrentMovie(

    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("status") val status: String
)

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

