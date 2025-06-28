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
import com.example.mymovie.MovieViewModel
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
    private val modelMovie: MovieViewModel by activityViewModels()

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


    private fun updateMovieList(movie: MovieModel) {
        val newList = listOf(movie) // Заменяем старый список новым
        adapter.submitList(newList)
    }



    companion object {
        @JvmStatic
        fun newInstance() = Fragment_Movie()
    }
}