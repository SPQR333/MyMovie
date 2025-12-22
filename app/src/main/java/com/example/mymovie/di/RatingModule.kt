package com.example.mymovie.di

import android.content.Context
import com.example.mymovie.data.repository.RatingRepositoryImpl
import com.example.mymovie.domain.repository.RatingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RatingModule {

    @Provides
    @Singleton
    fun provideRatingRepository(
        @ApplicationContext context: Context
    ): RatingRepository {
        return RatingRepositoryImpl(context)
    }
}