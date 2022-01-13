package com.bogatovnikita.myweather.utils

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.bogatovnikita.myweather.BuildConfig
import com.bogatovnikita.myweather.model.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(private val onWeatherLoader: OnWeatherLoader) {
    //lateinit var weatherDTO: WeatherDTO
    //lateinit var httpsURLConnection: HttpsURLConnection

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadWeather(lat: Double, lon: Double) {
        try {
            Thread {
                val url = URL("https://api.weather.yandex.ru/v2/informers?&lat=$lat&lon=$lon")
                val httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {
                    requestMethod = "GET"
                    readTimeout = 2000
                    addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
                }
                val bufferedReader =
                    BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
                val weatherDTO: WeatherDTO? = Gson().fromJson(
                    converterBufferedToResult(bufferedReader),
                    WeatherDTO::class.java
                )
                Handler(Looper.getMainLooper()).post {
                    onWeatherLoader.onLoaded(weatherDTO)
                }
            }.start()
        } catch (e: Exception) {
            e.printStackTrace()
            //onWeatherLoader.onFailed(weatherDTO)
        } finally {
            //httpsURLConnection.disconnect()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun converterBufferedToResult(bufferedReader: BufferedReader): String {
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }

    interface OnWeatherLoader {
        fun onLoaded(weatherDTO: WeatherDTO?)
        fun onFailed(weatherDTO: WeatherDTO)
    }
}