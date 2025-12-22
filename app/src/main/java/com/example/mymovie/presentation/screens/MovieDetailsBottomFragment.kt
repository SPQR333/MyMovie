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
import androidx.lifecycle.repeatOnLifecycle
import com.example.mymovie.domain.model.Movie
import com.example.mymovie.presentation.viewModels.MovieViewModel
import com.example.mymovie.databinding.FragmentMovieDetailBinding
import com.example.mymovie.domain.repository.RatingRepository
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
        setupRatingObserver()
        setupRatingListener()
        loadRating()

    }

    private fun loadRating() {
        // Загружаем сохраненный рейтинг
        currentMovie?.let { movie ->
            modelMovie.loadRatingForMovie(movie.id)
        }
    }

    private fun setupRatingObserver() {
        // Подписываемся на изменения рейтинга
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                modelMovie.ratingFlow.collect { rating ->
                    // Обновляем UI при изменении рейтинга
                    binding.ratingBar.rating = rating.toFloat()
                    updateRatingText(rating)
                }
            }
        }
    }

    private fun setupRatingListener() {
        currentMovie?.let { movie ->
            // Real-time обновление текста при выборе
            binding.ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
                if (fromUser) {
                    updateRatingText(rating.toInt())
                }
            }
        }
    }

    private fun setupRating() {
        currentMovie?.let { movie ->
            // Подписываемся на Flow
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    modelMovie.ratingFlow.collect { rating ->
                        // Здесь rating уже Int!
                        binding.ratingBar.rating = rating.toFloat()
                        updateRatingText(rating)
                    }
                }
            }

            // 2. Обновляем текст в реальном времени
            binding.ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
                if (fromUser) {
                    updateRatingText(rating.toInt())
                }
            }

            // 3. Сохраняем по кнопке
            binding.btnSubmitRating.setOnClickListener {
                val rating = binding.ratingBar.rating.toInt()
                lifecycleScope.launch {
                    modelMovie.saveRating(movie.id, rating)
                    updateRatingText(rating)
                    Toast.makeText(requireContext(), "Оценка сохранена!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }






   /* private fun setupRating() {
        // Загружаем сохраненный рейтинг
        currentMovie?.let { movie ->
            val savedRating = modelMovie.loadRatingForMovie(movie.id)
            binding.ratingBar.rating = savedRating
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
    }*/

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


