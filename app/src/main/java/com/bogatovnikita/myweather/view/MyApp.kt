package com.bogatovnikita.myweather.view

import android.app.Application
import retrofit2.Retrofit

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: MyApp? = null
        val retrofit = Retrofit.Builder()
    }
}