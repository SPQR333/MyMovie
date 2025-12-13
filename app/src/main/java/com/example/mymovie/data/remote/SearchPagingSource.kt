// data/remote/SearchPagingSource.kt
package com.example.mymovie.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mymovie.data.api.MovieApi
import com.example.mymovie.data.mapper.MovieMapper
import com.example.mymovie.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchPagingSource @Inject constructor(
    private val api: MovieApi,
    private val mapper: MovieMapper,
    private val query: String // Поисковый запрос
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1

            // ИСПОЛЬЗУЕМ API ПОИСКА вместо популярных фильмов
            val response = api.searchMovies(
                query = query,
                page = page
            )

            LoadResult.Page(
                data = response.results.map { mapper.mapToDomain(it) },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.total_pages) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}