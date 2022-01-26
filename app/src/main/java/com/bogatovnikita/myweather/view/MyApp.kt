package com.bogatovnikita.myweather.view

import android.app.Application
import retrofit2.Retrofit

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        val retrofit = Retrofit.Builder()
    }
}