package com.example.mymovie.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FriendshipDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(friendship: Friendship)

    @Query("""
        SELECT COUNT(*) FROM friendships 
        WHERE user_id = :userId AND status = 'accepted'
    """)
    suspend fun getFriendsCount(userId: Long): Int

    @Query("""
        SELECT * FROM friendships 
        WHERE user_id = :userId AND status = :status
    """)
    suspend fun getFriendshipsByStatus(userId: Long, status: String): List<Friendship>

    @Query("""
        UPDATE friendships 
        SET status = 'accepted' 
        WHERE user_id = :userId AND friend_id = :friendId
    """)
    suspend fun acceptFriendship(userId: Long, friendId: Long)
}