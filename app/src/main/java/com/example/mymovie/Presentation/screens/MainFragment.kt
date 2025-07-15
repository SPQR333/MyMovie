package com.example.mymovie.Presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mymovie.Data.api.MovieApi
import com.example.mymovie.Presentation.viewModels.MovieViewModel
import com.example.mymovie.R
import com.example.mymovie.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var movieApi: MovieApi
    private lateinit var binding: FragmentMainBinding
    private val model: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        childFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, Fragment_Movie())
            .commit()


        return binding.root


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loadPopularMovies()


    }

    private fun loadPopularMovies() {
        lifecycleScope.launch {
            try {
                // Делаем запрос
                val response = movieApi.getPopularMovie()

                // Выводим результат в логи
                Log.d("API_RESPONSE", "Успешный ответ: ${response.results.size} фильмов")
                response.results.forEach { movie ->
                    Log.d("MOVIE_DETAILS", """
                        $response
                        Название: ${movie.id}
                        Рейтинг: ${movie.voteAverage}
                        Постер: ${movie.posterPath ?: "нет"}
                    """.trimIndent())
                }
            } catch (e: Exception) {
                // Обрабатываем ошибки
                Log.e("API_ERROR", "Ошибка запроса", e)
            }
        }
        }
}