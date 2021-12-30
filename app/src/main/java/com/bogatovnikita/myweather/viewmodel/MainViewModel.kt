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

    fun getWeatherFromLocalSourceRus() = getWeatherFromServer(true)
    fun getWeatherFromLocalSourceWorld() = getWeatherFromServer(true)


    fun getWeatherFromServer(isRussian: Boolean) {
        liveData.postValue(AppState.Loading(0))
        Thread {
            sleep(2000)
            val rand = (1..10).random()
            if (rand >= 9) {
                liveData.postValue(
                    AppState.Success(
                        if (isRussian) {
                            repo.getWeatherFromLocalStorageRus()
                        } else {
                            repo.getWeatherFromLocalStorageWorld()
                        }
                    )
                )
            } else {
                liveData.postValue(AppState.Error(IllegalAccessException()))
            }
        }.start()
    }
}