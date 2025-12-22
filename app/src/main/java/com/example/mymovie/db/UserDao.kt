package com.example.mymovie.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Long): User?

    // ✅ ПРАВИЛЬНЫЙ запрос для UserProfile
    @Query("""
        SELECT 
            users.*,
            COALESCE((
                SELECT COUNT(*) 
                FROM friendships 
                WHERE user_id = users.id 
                AND status = 'accepted'
            ), 0) as friends_count,
            COALESCE((
                SELECT COUNT(*) 
                FROM user_movies 
                WHERE user_id = users.id 
                AND status = 'rated'
            ), 0) as movies_rated,
            COALESCE((
                SELECT COUNT(*) 
                FROM user_movies 
                WHERE user_id = users.id 
                AND status = 'watchlist'
            ), 0) as movies_watchlist
        FROM users
        WHERE users.id = :userId
    """)
    suspend fun getUserProfile(userId: Long): UserProfile?

    // Альтернативно: получить статистику отдельно
    @Query("""
        SELECT COUNT(*) FROM friendships 
        WHERE user_id = :userId AND status = 'accepted'
    """)
    suspend fun getFriendsCount(userId: Long): Int

    @Query("""
        SELECT COUNT(*) FROM user_movies 
        WHERE user_id = :userId AND status = 'rated'
    """)
    suspend fun getRatedMoviesCount(userId: Long): Int

    @Query("""
        SELECT COUNT(*) FROM user_movies 
        WHERE user_id = :userId AND status = 'watchlist'
    """)
    suspend fun getWatchlistCount(userId: Long): Int
}