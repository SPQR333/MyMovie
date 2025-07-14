package com.example.mymovie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovie.Domain.model.MovieModel

class MainViewModel: ViewModel() {
    val liveDataCurrent = MutableLiveData<MovieModel>()
    val liveDataList = MutableLiveData<List<MovieModel>>()
}
