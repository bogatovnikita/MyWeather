package com.bogatovnikita.myweather.repository

import okhttp3.Callback

interface RepositoryDetails {
    fun getWeatherFromServer(url: String, callback: Callback)
}