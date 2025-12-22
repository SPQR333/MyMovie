package com.example.mymovie.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// UserMovieDao.kt
@Dao
interface UserMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userMovie: UserMovie)

    @Delete
    suspend fun delete(userMovie: UserMovie)

    // Получить фильмы пользователя по статусу
    @Query("SELECT * FROM user_movies WHERE user_id = :userId AND status = :status")
    suspend fun getUserMoviesByStatus(userId: Long, status: String): List<UserMovie>

    // Получить количество фильмов по статусу
    @Query("""
        SELECT COUNT(*) FROM user_movies 
        WHERE user_id = :userId AND status = :status
    """)
    suspend fun getCountByStatus(userId: Long, status: String): Int
}