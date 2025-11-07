package com.example.mymovie.presentation.viewModels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.mymovie.data.api.MovieApi
import com.example.mymovie.data.mapper.MovieMapper
import com.example.mymovie.data.remote.MoviePagingSource
import com.example.mymovie.data.remote.SearchPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieApi: MovieApi,
    private val mapper: MovieMapper,
) : ViewModel() {



    // Текущая работающая реализация
    val popularMovies = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(movieApi, mapper) }
    ).flow.cachedIn(viewModelScope)

    // ДОБАВИМ поиск рядом с существующим кодом
    private val _searchQuery = MutableStateFlow("")

    val movies = _searchQuery
        .debounce(500)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                popularMovies // Возвращаем популярные если запрос пустой
            } else {
                // Новый Pager для поиска
                Pager(
                    config = PagingConfig(pageSize = 20),
                    pagingSourceFactory = { SearchPagingSource(movieApi, mapper, query) }
                ).flow.cachedIn(viewModelScope)
            }
        }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
}