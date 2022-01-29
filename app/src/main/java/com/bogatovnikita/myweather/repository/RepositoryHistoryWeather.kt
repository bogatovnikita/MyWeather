package com.bogatovnikita.myweather.repository

import com.bogatovnikita.myweather.model.Weather

interface RepositoryHistoryWeather {
    fun getAllHistoryWeather(): List<Weather>
    fun saveWeather(weather: Weather)
}