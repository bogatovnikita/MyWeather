package com.bogatovnikita.myweather.view

import android.app.Application
import androidx.room.Room
import com.bogatovnikita.myweather.DB_NAME
import com.bogatovnikita.myweather.R
import com.bogatovnikita.myweather.room.HistoryDatabase
import com.bogatovnikita.myweather.room.HistoryWeatherDao
import retrofit2.Retrofit
import java.util.*

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: MyApp? = null
        val retrofit = Retrofit.Builder()
        private var database: HistoryDatabase? = null

        fun getHistoryWeatherDao(): HistoryWeatherDao {
            if (database == null) {
                if (appInstance == null) {
                    throw IllformedLocaleException(R.string.error.toString())
                } else {
                    database = Room.databaseBuilder(
                        appInstance!!.applicationContext, HistoryDatabase::class.java,
                        DB_NAME
                    )
                        .build()
                }
            }
            return database!!.historyWeatherDao()
        }
    }
}