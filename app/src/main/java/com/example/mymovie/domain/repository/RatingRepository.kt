package com.example.mymovie.domain.repository

import android.media.Rating

interface RatingRepository {
    fun saveRating(movieId:Int,rating: Int)
    fun getRating(movieId: Int): Int

}