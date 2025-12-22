package com.example.mymovie.db

import androidx.room.ColumnInfo
import androidx.room.Embedded

// Если хотите использовать в Room запросах, нужно создать специальный data class
data class UserProfile(
    @Embedded
    val user: User,

    @ColumnInfo(name = "friends_count")
    val friendsCount: Int,

    @ColumnInfo(name = "movies_rated")
    val moviesRated: Int,

    @ColumnInfo(name = "movies_watchlist")
    val moviesWatchlist: Int
)

// ❌ УДАЛИТЕ эту функцию - она не нужна в Entity файле
// suspend fun getUserProfile(userId: Long): UserProfile?