package com.bogatovnikita.myweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class MainViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData()) : ViewModel() {

    fun getLiveData(): LiveData<AppState> {
        return liveData
    }

    fun getWeatherFromServer() {
        liveData.postValue(AppState.Loading(0))
        Thread {
            sleep(2000)
            liveData.postValue(AppState.Success("Cold"))
        }.start()
    }
}