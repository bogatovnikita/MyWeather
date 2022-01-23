package com.bogatovnikita.myweather.repository

import com.bogatovnikita.myweather.model.WeatherDTO

interface RepositoryDetails {
    fun getWeatherFromServer(lat: Double, lon: Double, callback: retrofit2.Callback<WeatherDTO>)
}