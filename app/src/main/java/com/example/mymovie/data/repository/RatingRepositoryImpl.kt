package com.example.mymovie.data.repository

import android.content.Context
import com.example.mymovie.domain.repository.RatingRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RatingRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : RatingRepository {

    companion object {
        private const val PREFS_NAME = "movie_ratings"
        private const val KEY_PREFIX = "rating_"
    }
    private val sharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun saveRating(movieId: Int, rating: Int) {
        sharedPreferences.edit()
            .putInt("$KEY_PREFIX$movieId", rating)
            .apply()
    }

    override fun getRating(movieId: Int): Int {
        return sharedPreferences.getInt("$KEY_PREFIX$movieId", 0)
    }
}