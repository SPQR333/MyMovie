package com.example.mymovie.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.mymovie.db.FriendshipDao
import com.example.mymovie.db.MainDb
import com.example.mymovie.db.UserDao
import com.example.mymovie.db.UserMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/*
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): MainDb {
        return MainDb.getDataBase(app)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("movie_app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideUserDao(db: MainDb): UserDao = db.userDao()

    @Provides
    fun provideUserMovieDao(db: MainDb): UserMovieDao = db.userMovieDao()

    @Provides
    fun provideFriendshipDao(db: MainDb): FriendshipDao = db.friendshipDao()
}
*/
