package com.example.mymovie.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mymovie.data.api.MovieApi
import com.example.mymovie.data.mapper.MovieMapper
import com.example.mymovie.data.model.ApiMovieResponse
import com.example.mymovie.domain.model.Movie
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val api: MovieApi,
    private val mapper: MovieMapper
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = api.getPopularMovie(page = page)

            LoadResult.Page(
                data = response.results.map { mapper.mapToDomain(it) }, // Преобразуем ApiMovie в Movie
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.total_pages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}