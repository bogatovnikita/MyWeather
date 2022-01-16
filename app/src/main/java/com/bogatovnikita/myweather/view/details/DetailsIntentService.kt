package com.bogatovnikita.myweather.view.details

import android.app.IntentService
import android.content.Intent
import com.bogatovnikita.myweather.*
import com.bogatovnikita.myweather.model.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class DetailsIntentService(name: String = "") : IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {
        intent?.let {

            loadWeather(
                intent.getDoubleExtra(BUNDLE_KEY_LAT, 0.0),
                intent.getDoubleExtra(BUNDLE_KEY_LON, 0.0)
            )
        }

    }

    private fun loadWeather(lat: Double, lon: Double) {
        lateinit var httpsURLConnection: HttpsURLConnection
        try {
            val url = URL("https://api.weather.yandex.ru/v2/informers?&lat=$lat&lon=$lon")
            httpsURLConnection =
                (url.openConnection() as HttpsURLConnection).apply {
                    requestMethod = "GET"
                    readTimeout = 2000
                    addRequestProperty(
                        X_YANDEX_API_KEY,
                        BuildConfig.WEATHER_API_KEY
                    )
                }
            val bufferedReader =
                BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
            val weatherDTO: WeatherDTO? = Gson().fromJson(
                converterBufferedToResult(bufferedReader),
                WeatherDTO::class.java
            )
            sendBroadcast(Intent(BROADCAST_INTENT_KEY).apply {
                putExtra(BUNDLE_KEY_WEATHER, weatherDTO)
            })

        } catch (e: Exception) {
            e.printStackTrace()

        } finally {
            httpsURLConnection.disconnect()
        }
    }

    private fun converterBufferedToResult(bufferedReader: BufferedReader): String {
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }
}