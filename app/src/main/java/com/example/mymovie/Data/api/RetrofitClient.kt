package com.example.mymovie.Data.api

import com.example.mymovie.Data.remote.MovieMapper
import com.example.mymovie.Data.repository.MovieRepositoryImpl
import com.example.mymovie.Domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetAddress
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val AUTH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZjZlOGRjOThjMzY5YjY5YTFjYjcwNzBkZGQ4MDc2NSIsIm5iZiI6MTc0NTQ0NjIxNS4xMzUsInN1YiI6IjY4MDk2NTQ3YjJiNzIyYWVkZjhhMmE1YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.CaeNCane7d3Mzo5mzd1Ic9mzJWQ3iJRD7J2z8DsF3eQ"

    // Для Retrofit
    @Provides
    fun provideMovieMapper(): MovieMapper = MovieMapper()

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val dns = object : Dns {
            override fun lookup(hostname: String): List<InetAddress> {
                return when (hostname) {
                    "api.themoviedb.org" -> {
                        listOf(
                            InetAddress.getByName("13.225.103.27"),
                            InetAddress.getByName("13.225.103.86")
                        )
                    }
                    "image.tmdb.org" -> {
                        listOf(
                            InetAddress.getByName("104.16.61.155"),
                            InetAddress.getByName("104.16.62.155")
                        )
                    }
                    else -> Dns.SYSTEM.lookup(hostname)
                }
            }
        }

        return OkHttpClient.Builder()
            .dns(dns)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", AUTH_TOKEN)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): MovieApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    fun provideMovieRepository(api: MovieApi): MovieRepository {
        return MovieRepositoryImpl(
            api,
            dispatcher = Dispatchers.IO,
            mapper = MovieMapper()
        )
    }

    // Для Glide (отдельный клиент)
    @Provides
    @Named("GlideClient")
    fun provideGlideOkHttpClient(): OkHttpClient {
        val dns = object : Dns {
            override fun lookup(hostname: String): List<InetAddress> {
                return if (hostname == "image.tmdb.org") {
                    listOf(
                        InetAddress.getByName("104.16.61.155"),
                        InetAddress.getByName("104.16.62.155")
                    )
                } else {
                    Dns.SYSTEM.lookup(hostname)
                }
            }
        }

        return OkHttpClient.Builder()
            .dns(dns)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}