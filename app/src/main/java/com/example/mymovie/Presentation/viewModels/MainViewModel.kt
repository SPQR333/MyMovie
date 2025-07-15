package com.example.mymovie.Presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovie.Domain.model.Movie

class MainViewModel: ViewModel() {
    val liveDataCurrent = MutableLiveData<Movie>()
    val liveDataList = MutableLiveData<List<Movie>>()
}
