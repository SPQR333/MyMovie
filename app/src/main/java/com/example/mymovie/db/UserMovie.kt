package com.example.mymovie.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

// UserMovie.kt
@Entity(
    tableName = "user_movies",
    primaryKeys = ["user_id", "movie_id"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
        // Если есть таблица Movie::class, добавьте второй ForeignKey
    ]
)
data class UserMovie(
    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "movie_id")
    val movieId: Long,  // ID фильма из TMDB или вашей базы

    @ColumnInfo(name = "status")
    val status: String,  // "rated", "watchlist", "watched"

    @ColumnInfo(name = "rating")
    val rating: Int? = null,  // Оценка 1-10

    @ColumnInfo(name = "review")
    val review: String? = null,

    @ColumnInfo(name = "added_date")
    val addedDate: Long = System.currentTimeMillis()
)
