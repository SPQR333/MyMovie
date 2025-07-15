package com.example.mymovie.Presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovie.Domain.adapters.MovieAdapter
import com.example.mymovie.Domain.model.Movie
import com.example.mymovie.Presentation.viewModels.MovieViewModel
import com.example.mymovie.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class Fragment_Movie : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MovieAdapter
    private val modelMovie: MovieViewModel by viewModels()

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
        modelMovie.loadPopularMovie()
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

    private fun showMovieDetails(movie: Movie) {

        Toast.makeText(requireContext(), "Selected: ${movie.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = Fragment_Movie()
    }
}