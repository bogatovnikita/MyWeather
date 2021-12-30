package com.bogatovnikita.myweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bogatovnikita.myweather.model.RepositoryImplemented
import java.lang.Thread.sleep

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repo: RepositoryImplemented = RepositoryImplemented()
) : ViewModel() {

    fun getLiveData(): LiveData<AppState> {
        return liveData
    }

    fun getWeatherFromServer() {
        liveData.postValue(AppState.Loading(0))
        Thread {
            sleep(2000)
            val rand = (1..40).random()
            if (rand > 20) liveData.postValue(AppState.Success(repo.getWeatherFromServer()))
            else liveData.postValue(AppState.Error(IllegalAccessException()))
        }.start()
    }
}