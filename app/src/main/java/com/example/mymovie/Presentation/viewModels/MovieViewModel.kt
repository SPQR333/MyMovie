package com.example.mymovie.Presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovie.Data.api.MovieApi
import com.example.mymovie.Domain.model.Movie
import com.example.mymovie.Domain.usecase.GetListMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieApi: MovieApi, // Инжектим Retrofit-сервис
    private val getListMoviesUseCase: GetListMovieUseCase
) : ViewModel() {

    private val _movie = MutableStateFlow<List<Movie>>(emptyList())
    val movie: StateFlow<List<Movie>> = _movie.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()




    fun loadPopularMovie() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _movie.value = getListMoviesUseCase()
            } catch (e: Exception) {
                Log.e("MovieFragment", "Error loading movie", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}