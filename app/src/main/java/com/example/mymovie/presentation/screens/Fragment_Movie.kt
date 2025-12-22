package com.example.mymovie.presentation.screens

import android.R.attr.action
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovie.domain.adapters.MovieAdapter
import com.example.mymovie.domain.model.Movie
import com.example.mymovie.presentation.NoSSLv3SocketFactory
import com.example.mymovie.presentation.viewModels.MovieViewModel
import com.example.mymovie.databinding.FragmentMovieBinding
import com.example.mymovie.presentation.viewModels.SharedViewModel
import com.google.android.material.internal.ViewUtils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Fragment_Movie : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MovieAdapter
    private val modelMovie: MovieViewModel by viewModels()

    // Используем activityViewModels() чтобы получить тот же SharedViewModel что и в MainFragment
    private val sharedViewModel: SharedViewModel by activityViewModels()

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
        observeMovies()
        observeSharedSearchQuery() // ← Слушаем поиск из MainFragment
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter { movie ->
            showMovieDetails(movie)
        }

        binding.rcView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@Fragment_Movie.adapter
            setHasFixedSize(true)
        }
    }

    private fun observeMovies() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                modelMovie.movies.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }

    // Слушаем поисковые запросы из MainFragment
    private fun observeSharedSearchQuery() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.searchQuery.collect { query ->
                    // Передаем запрос в MovieViewModel
                    modelMovie.setSearchQuery(query)

                    // Опционально: обновляем UI
                }
            }
        }
    }



    private fun showMovieDetails(movie: Movie) {
        val dialog = MovieDetailsBottomFragment.newInstance(movie)
        // Используем parentFragmentManager т.к. мы дочерний фрагмент
        dialog.show(parentFragmentManager, MovieDetailsBottomFragment.TAG)
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