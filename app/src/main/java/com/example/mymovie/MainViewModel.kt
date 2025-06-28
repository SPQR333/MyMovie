package com.example.mymovie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovie.Domain.model.MovieModel
import com.example.mymovie.Domain.model.SimpleMovie

class MainViewModel: ViewModel() {
    val liveDataCurrent = MutableLiveData<MovieModel>()
    val liveDataList = MutableLiveData<List<MovieModel>>()
}
