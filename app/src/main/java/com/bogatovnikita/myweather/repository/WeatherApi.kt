package com.bogatovnikita.myweather.repository

import com.bogatovnikita.myweather.X_YANDEX_API_KEY
import com.bogatovnikita.myweather.YANDEX_API_URL_END_POINT
import com.bogatovnikita.myweather.model.WeatherDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApi {
    @GET(YANDEX_API_URL_END_POINT)
    fun getWeather(
        @Header(X_YANDEX_API_KEY) apikey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>
}