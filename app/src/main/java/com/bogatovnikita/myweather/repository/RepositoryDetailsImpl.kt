package com.bogatovnikita.myweather.repository

import com.bogatovnikita.myweather.BuildConfig
import com.bogatovnikita.myweather.X_YANDEX_API_KEY
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

class RepositoryDetailsImpl : RepositoryDetails {
    override fun getWeatherFromServer(url: String, callback: Callback) {
        val builder = Request.Builder().apply {
            header(X_YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
            url(url)
        }
        OkHttpClient().newCall(builder.build()).enqueue(callback)
    }
}