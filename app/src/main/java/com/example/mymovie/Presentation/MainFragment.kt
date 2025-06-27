package com.example.mymovie.Presentation

import android.Manifest
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mymovie.Data.api.MovieApi
import com.example.mymovie.Data.api.RetrofitClient
import com.example.mymovie.Domain.adapters.MovieAdapter
import com.example.mymovie.Domain.model.MovieModel
import com.example.mymovie.MainViewModel
import com.example.mymovie.MovieViewModel
import com.example.mymovie.R
import com.example.mymovie.databinding.FragmentMainBinding
import com.example.mymovie.fragments.isPermissionGranted
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: MovieAdapter
    private val model: MovieViewModel by viewModels()
    private lateinit var pLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, Fragment_Movie.newInstance())
            .commit()
            getPopularListMovie()
    }




    private fun getPopularListMovie() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                Log.d("API_REQUEST", "Отправка запроса...")

                val response = RetrofitClient.apiService.getPopularMovies(
                    apiKey = "6f6e8dc98c369b69a1cb7070ddd80765" // ваш ключ
                )

                Log.d("API_RESPONSE", "Успешный ответ: ${response.results} фильмов")
                response.results.forEach { movie ->
                    Log.d(
                        "MOVIE_DATA", """
                        Title: ${movie.title}
                        Rating: ${movie.voteAverage}
                        Poster: ${movie.posterPath}
                        Release: ${movie.releaseDate}
                        --------------------------
                    """.trimIndent()
                    )
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Ошибка запроса: ${e.message}")
            }
        }
    }


    private fun parseMovieData(response: String) {
        val mainObject = JSONObject(response)
    }

    private fun updateMovieList(movie: MovieModel) {

    }


}











