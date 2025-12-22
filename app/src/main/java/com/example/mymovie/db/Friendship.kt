package com.example.mymovie.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "friendships",
    primaryKeys = ["user_id", "friend_id"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["friend_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Friendship(
    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "friend_id")
    val friendId: Long,

    @ColumnInfo(name = "status")
    val status: String = "pending"  // "pending", "accepted", "blocked"
)