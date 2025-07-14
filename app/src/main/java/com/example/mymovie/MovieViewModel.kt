package com.example.mymovie

import android.app.Person
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovie.Data.api.MovieApi
import com.example.mymovie.Domain.MovieItem
import com.example.mymovie.Domain.model.MovieModel
import com.example.mymovie.Domain.usecase.GetListMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Response
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieApi: MovieApi, // Инжектим Retrofit-сервис
    private val getListMoviesUseCase: GetListMovieUseCase
) : ViewModel() {

    private val _movie = MutableStateFlow<List<MovieModel>>(emptyList())
    val movie: StateFlow<List<MovieModel>> = _movie.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()




    fun loadPopularMovie() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _movie.value = getListMoviesUseCase() // Используем только UseCase
            } catch (e: Exception) {
                Log.e("MovieFragment", "Error loading people", e)
                // Можно добавить _errorState для обработки ошибок в UI
            } finally {
                _isLoading.value = false
            }
        }
    }
}