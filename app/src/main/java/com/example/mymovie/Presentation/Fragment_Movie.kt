package com.example.mymovie.Presentation

import android.content.ContentValues.TAG
import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mymovie.Domain.adapters.MovieAdapter
import com.example.mymovie.Domain.model.MovieModel
import com.example.mymovie.MainViewModel
import com.example.mymovie.R
import com.example.mymovie.databinding.FragmentMainBinding
import com.example.mymovie.databinding.FragmentMovieBinding
import com.example.mymovie.databinding.ListItemBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject


class Fragment_Movie : Fragment() {
    private lateinit var binding: FragmentMovieBinding
    private lateinit var adapter: MovieAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        requestMovieData()


    }




    private fun initRcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = MovieAdapter()
        rcView.adapter = adapter

            val list = listOf(

                MovieModel("GodFather$","111"),
                MovieModel("Club","222"),
                MovieModel("Club","333"),
                MovieModel("Club","444")


        )
        adapter.submitList(list)

    }

    private fun requestMovieData() {
        // 1. Используем HTTP (временно, для обхода SSL проблем)
        val ipAddress = "13.225.103.26" // Актуальный IPv4 TMDb
        val url = "http://$ipAddress/3/movie/550?api_key=6f6e8dc98c369b69a1cb7070ddd80765&language=ru-RU"

        // 2. Настройка очереди с очисткой кеша
        val queue = Volley.newRequestQueue(requireContext()).apply {
            cache.clear()
        }

        // 3. Создаем запрос с обязательными заголовками
        val request = object : StringRequest(
            Method.GET, url,
            { response ->
                parseMovieData(response)
                try {
                    val json = JSONObject(response)
                    val title = json.getString("title")
                    Log.d("TAG1", "Успех! Название: $json,$title")
                } catch (e: Exception) {
                    Log.e("TAG", "Ошибка парсинга", e)
                }
            },
            { error ->
                Log.e(TAG, "Ошибка запроса: ${error.message}")
            }
        ) {
            override fun getHeaders() = mapOf(
                "Host" to "api.themoviedb.org",
                "User-Agent" to "MyMovieApp/1.0",
                "Accept" to "application/json"
            )
        }.apply {
            retryPolicy = DefaultRetryPolicy(
                15000, // 15 секунд таймаут
                0,     // Без повторов
                1f
            )
        }

        // 4. Отправляем запрос
        queue.add(request)
    }

    private fun parseMovieData(response: String) {
        val mainObject = JSONObject(response)
        parseCurrentData(mainObject)
    }
    private fun updateMovieList(movie: MovieModel) {
        val newList = listOf(movie) // Заменяем старый список новым
        adapter.submitList(newList)
    }




        private fun parseCurrentData(mainObject: JSONObject) {
            val id = mainObject.getInt("id")
            val title = mainObject.getString("title")
            val poster_path = mainObject.getString("poster_path")








            Log.d("MyLog", "image: ${mainObject.getString("poster_path")}")




        }


    companion object {
        @JvmStatic
        fun newInstance() = Fragment_Movie()
    }
}