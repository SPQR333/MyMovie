package com.example.mymovie.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.mymovie.domain.model.Movie
import com.example.mymovie.presentation.viewModels.MovieViewModel
import com.example.mymovie.databinding.FragmentMovieDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailsBottomFragment : BottomSheetDialogFragment() {
    private val modelMovie: MovieViewModel by activityViewModels()
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private var currentMovie: Movie? = null
    private var ratingJob: Job? = null

    companion object {
        const val TAG = "MovieDetailsBottomFragment"

        fun newInstance(movie: Movie? = null): MovieDetailsBottomFragment {
            val fragment = MovieDetailsBottomFragment()
            movie?.let {
                val args = Bundle()
                args.putParcelable("movie", it)
                fragment.arguments = args
            }
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        currentMovie = arguments?.getParcelable<Movie>("movie")
        setupUI()
        setupRating()

    }


    private fun setupRating() {
        // Загружаем сохраненный рейтинг
        currentMovie?.let { movie ->
            val savedRating = modelMovie.getRating(movie.id)
            binding.ratingBar.rating = savedRating.toFloat()
            updateRatingText(savedRating)



        }

        // Обработка выбора рейтинга
        binding.ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) {
                binding.tvCurrentRating.text = "Оценка: ${rating.toInt()}/5"
            }
        }

        // Кнопка подтверждения
        binding.btnSubmitRating.setOnClickListener {
            currentMovie?.let { movie ->
                val rating = binding.ratingBar.rating.toInt()
                modelMovie.saveRating(movie.id, rating)
                updateRatingText(rating)
                Toast.makeText(requireContext(), "Оценка $rating сохранена!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateRatingText(rating: Int) {
        binding.tvCurrentRating.text = "Оценка: $rating/5"
    }

    private fun setupUI() {
        currentMovie?.let { movie ->
            binding.descriptionMovie.text = movie.overview
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ratingJob?.cancel()
        _binding = null
    }

}

