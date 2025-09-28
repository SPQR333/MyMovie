package com.example.mymovie.Presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.mymovie.Domain.model.Movie
import com.example.mymovie.Presentation.viewModels.MovieViewModel
import com.example.mymovie.databinding.FragmentMovieDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsBottomFragment : BottomSheetDialogFragment() {
    private val modelMovie: MovieViewModel by activityViewModels()
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

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
        binding.textView
        binding.ratingBar



    }
}