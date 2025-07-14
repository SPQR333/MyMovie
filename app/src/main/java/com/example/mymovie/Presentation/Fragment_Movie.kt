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
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mymovie.Domain.adapters.MovieAdapter
import com.example.mymovie.Domain.model.MovieModel
import com.example.mymovie.MainViewModel
import com.example.mymovie.MovieViewModel
import com.example.mymovie.R
import com.example.mymovie.databinding.FragmentMainBinding
import com.example.mymovie.databinding.FragmentMovieBinding
import com.example.mymovie.databinding.ListItemBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONObject


@AndroidEntryPoint
class Fragment_Movie : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MovieAdapter
    private val modelMovie: MovieViewModel by viewModels() // Используйте viewModels() вместо activityViewModels(), если не нужно разделять состояние между фрагментами

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeMovieList()
        modelMovie.loadPopularMovie() // Загружаем данные после настройки RecyclerView
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter { movie ->
            // Обработка клика по элементу
            showMovieDetails(movie)
        }

        binding.rcView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@Fragment_Movie.adapter
            setHasFixedSize(true) // Оптимизация для фиксированного размера элементов
        }
    }

    private fun observeMovieList() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                modelMovie.movie.collect { movies ->
                    adapter.submitList(movies)
                }
            }
        }
    }

    private fun showMovieDetails(movie: MovieModel) {
        // Реализуйте навигацию к деталям фильма
        Toast.makeText(requireContext(), "Selected: ${movie.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Очищаем binding при уничтожении View
    }

    companion object {
        @JvmStatic
        fun newInstance() = Fragment_Movie()
    }
}