package com.example.mymovie.presentation.viewModels

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.mymovie.data.api.MovieApi
import com.example.mymovie.data.mapper.MovieMapper
import com.example.mymovie.data.remote.MoviePagingSource
import com.example.mymovie.data.remote.SearchPagingSource
import com.example.mymovie.domain.model.Movie
import com.example.mymovie.domain.repository.RatingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieApi: MovieApi,
    private val mapper: MovieMapper,
    private val ratingRepository: RatingRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _ratingFlow = MutableStateFlow(0)
    val ratingFlow: StateFlow<Int> = _ratingFlow.asStateFlow()


    fun saveRating(movieId: Int, rating: Int) {
        viewModelScope.launch {
            ratingRepository.saveRating(movieId, rating)
            _ratingFlow.value = rating
        }
    }


    fun loadRatingForMovie(movieId: Int) {
        viewModelScope.launch {
            val rating = ratingRepository.getRating(movieId)
            _ratingFlow.value = rating
        }
    }


    // Текущая работающая реализация
    val popularMovies = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(movieApi, mapper) }
    ).flow.cachedIn(viewModelScope)

    // Поиск
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