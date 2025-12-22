package com.example.mymovie.di

import android.app.Application
import com.example.mymovie.db.MainDb
import com.example.mymovie.db.FriendshipDao
import com.example.mymovie.db.UserDao
import com.example.mymovie.db.UserMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MainDb {
        return MainDb.getDatabase(app)
    }

    @Provides
    fun provideUserDao(database: MainDb): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideFriendshipDao(database: MainDb): FriendshipDao {
        return database.friendshipDao()
    }

    @Provides
    fun provideUserMovieDao(database: MainDb): UserMovieDao {
        return database.userMovieDao()
    }
}