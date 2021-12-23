package com.bogatovnikita.myweather.model

data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 20,
    val feelsLike: Int = 20
)

data class City(val name: String, val lon: Double, val lat: Double)

fun getDefaultCity() = City("Saint-Petersburg", 59.5, 30.3)