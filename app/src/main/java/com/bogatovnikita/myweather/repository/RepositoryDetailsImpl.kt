package com.bogatovnikita.myweather.repository

import com.bogatovnikita.myweather.BuildConfig
import com.bogatovnikita.myweather.YANDEX_API_URL
import com.bogatovnikita.myweather.model.WeatherDTO
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryDetailsImpl : RepositoryDetails {
    override fun getWeatherFromServer(lat: Double, lon: Double, callback: Callback<WeatherDTO>) {
        val retrofit = Retrofit.Builder()
            .baseUrl(YANDEX_API_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .build().create(WeatherApi::class.java)
        retrofit.getWeather(BuildConfig.WEATHER_API_KEY, lat, lon).enqueue(callback)
    }
}