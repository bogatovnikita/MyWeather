package com.bogatovnikita.myweather.repository

import com.bogatovnikita.myweather.model.City
import com.bogatovnikita.myweather.model.Weather
import com.bogatovnikita.myweather.model.getRussianCities
import com.bogatovnikita.myweather.model.getWorldCities
import com.bogatovnikita.myweather.room.HistoryWeatherEntity
import com.bogatovnikita.myweather.view.MyApp

class RepositoryCityListImpl : RepositoryCityList, RepositoryHistoryWeather {
    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()

    override fun getAllHistoryWeather(): List<Weather> {
        return convertHistoryWeatherEntityToWeather(
            MyApp.getHistoryWeatherDao().getAllHistoryWeather()
        )
    }

    override fun saveWeather(weather: Weather) {
        MyApp.getHistoryWeatherDao().insert(convertWeatherToHistoryWeatherEntity(weather))
    }

    private fun convertHistoryWeatherEntityToWeather(entityList: List<HistoryWeatherEntity>): List<Weather> {
        return entityList.map {
            Weather(
                City(it.city, 0.0, 0.0), it.temperature, it.feelsLike, it.icon
            )
        }
    }

    private fun convertWeatherToHistoryWeatherEntity(weather: Weather) =
        HistoryWeatherEntity(
            0,
            weather.city.name,
            weather.temperature,
            weather.feelsLike,
            weather.icon
        )
}