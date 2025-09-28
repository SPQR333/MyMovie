package com.example.mymovie.Presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.mymovie.Data.api.MovieApi
import com.example.mymovie.Data.remote.MovieMapper
import com.example.mymovie.Data.remote.MoviePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieApi: MovieApi,
    private val mapper: MovieMapper,
) : ViewModel() {


    val movies = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(movieApi, mapper)}
    ).flow.cachedIn(viewModelScope)
}