package com.example.mymovie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovie.Data.api.Movie
import com.example.mymovie.Data.api.MovieApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val movieApi: MovieApi
) : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    fun loadPopularMovies() {
        viewModelScope.launch {
            try {
                val response = movieApi.getPopularMovies(
                    apiKey = "6f6e8dc98c369b69a1cb7070ddd80765",
                    language = "ru-RU"
                )
                _movies.value = response.results

                Log.d("Retrofit", "Успешный ответ: ${response.results}")

            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }
}
