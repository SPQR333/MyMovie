package com.example.mymovie.presentation.screens

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mymovie.data.api.MovieApi
import com.example.mymovie.presentation.viewModels.MovieViewModel
import com.example.mymovie.R
import com.example.mymovie.databinding.FragmentMainBinding
import com.example.mymovie.presentation.viewModels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    @Inject
    lateinit var movieApi: MovieApi
    private lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchView() // ← ДОБАВЬТЕ ЭТО
        setupFragment()
    }

    //  Настройка SearchView
    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    sharedViewModel.setSearchQuery(it)
                    hideKeyboard()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { sharedViewModel.setSearchQuery(it) }
                return true
            }
        })

        // Очистка поиска при закрытии
        binding.searchView.setOnCloseListener {
            sharedViewModel.clearSearch()
            false
        }
    }

    // Скрытие клавиатуры
    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
    }

    private fun setupFragment() {
        // Добавляем Fragment_Movie только если его еще нет
        if (childFragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            childFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, Fragment_Movie.newInstance())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Очищаем поиск при уничтожении фрагмента
        sharedViewModel.clearSearch()
    }
}