package com.bogatovnikita.myweather.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bogatovnikita.myweather.R
import com.bogatovnikita.myweather.TAG_E
import com.bogatovnikita.myweather.YANDEX_API_URL
import com.bogatovnikita.myweather.YANDEX_API_URL_END_POINT
import com.bogatovnikita.myweather.model.Weather
import com.bogatovnikita.myweather.model.WeatherDTO
import com.bogatovnikita.myweather.model.getDefaultCity
import com.bogatovnikita.myweather.repository.RepositoryDetailsImpl
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class DetailsViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData()) :
    ViewModel() {

    private val repositoryDetailsImpl: RepositoryDetailsImpl by lazy {
        RepositoryDetailsImpl()
    }

    fun getLiveData() = liveData

    fun getWeatherFromServer(lat: Double, lon: Double) {
        repositoryDetailsImpl.getWeatherFromServer(
            YANDEX_API_URL + YANDEX_API_URL_END_POINT +
                    "?lat=${lat}&lon=${lon}", callback
        )
    }

    fun converterDTOtoModel(weatherDTO: WeatherDTO): List<Weather> {
        return listOf(
            Weather(
                getDefaultCity(),
                weatherDTO.fact.temp.toInt(),
                weatherDTO.fact.feelsLike.toInt()
            )
        )
    }

    private val callback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            throw IOException(R.string.IOE_exception.toString())
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                response.body()?.let {
                    val json = it.string()
                    liveData.postValue(
                        AppState.Success(
                            converterDTOtoModel(
                                Gson().fromJson(
                                    json,
                                    WeatherDTO::class.java
                                )
                            )
                        )
                    )
                }
            } else {
                when (response.code()) {
                    400 -> Log.e(TAG_E, R.string.bad_request.toString())
                    401 -> Log.e(TAG_E, R.string.unauthorized.toString())
                    402 -> Log.e(TAG_E, R.string.payment_required.toString())
                    403 -> Log.e(TAG_E, R.string.forbidden.toString())
                    404 -> Log.e(TAG_E, R.string.not_found.toString())
                }
            }
        }
    }
}