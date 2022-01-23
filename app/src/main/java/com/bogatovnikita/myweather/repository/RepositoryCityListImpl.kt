package com.bogatovnikita.myweather.repository

import com.bogatovnikita.myweather.model.getRussianCities
import com.bogatovnikita.myweather.model.getWorldCities

class RepositoryCityListImpl : RepositoryCityList {
    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()

}