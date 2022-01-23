package com.bogatovnikita.myweather.repository

import com.bogatovnikita.myweather.model.Weather

interface RepositoryCityList {
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}