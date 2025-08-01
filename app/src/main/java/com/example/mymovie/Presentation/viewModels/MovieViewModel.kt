package com.example.mymovie.Presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.mymovie.Data.api.MovieApi
import com.example.mymovie.Data.remote.MovieMapper
import com.example.mymovie.Data.remote.MoviePagingSource
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
    private val movieApi: MovieApi,
    private val mapper: MovieMapper,
) : ViewModel() {

    private val _movie = MutableStateFlow<List<Movie>>(emptyList())
    val movie: StateFlow<List<Movie>> = _movie.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    val movies = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(movieApi, mapper)}
    ).flow.cachedIn(viewModelScope)
}