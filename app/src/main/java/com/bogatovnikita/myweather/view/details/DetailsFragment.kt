package com.bogatovnikita.myweather.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bogatovnikita.myweather.*
import com.bogatovnikita.myweather.databinding.FragmentDetailsBinding
import com.bogatovnikita.myweather.model.Weather
import com.bogatovnikita.myweather.model.WeatherDTO
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class DetailsFragment : Fragment() {

    private var client: OkHttpClient? = null
    private lateinit var localWeather: Weather
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    companion object {
        fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            it.getParcelable<Weather>(BUNDLE_KEY)?.let {
                localWeather = it
                getWeather()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getWeather() {
        if (client == null) client = OkHttpClient()
        val builder = Request.Builder().apply {
            header(X_YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
            url(
                YANDEX_API_URL + YANDEX_API_URL_END_POINT +
                        "?lat=${localWeather.city.lat}&lon=${localWeather.city.lon}"
            )
        }
        val request = builder.build()
        val call = client?.newCall(request)

        call?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(requireContext(), R.string.error, LENGTH_LONG).show()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val json = it.string()
                        requireActivity().runOnUiThread {
                            setWeatherData(Gson().fromJson(json, WeatherDTO::class.java))
                        }
                    }
                } else {
                    requireActivity().runOnUiThread {
                        when (response.code()) {
                            400 -> Toast.makeText(
                                requireContext(),
                                R.string.bad_request,
                                LENGTH_LONG
                            )
                                .show()
                            401 -> Toast.makeText(
                                requireContext(),
                                R.string.unauthorized,
                                LENGTH_LONG
                            )
                                .show()
                            402 -> Toast.makeText(
                                requireContext(),
                                R.string.payment_required,
                                LENGTH_LONG
                            )
                                .show()
                            403 -> Toast.makeText(requireContext(), R.string.forbidden, LENGTH_LONG)
                                .show()
                            404 -> Toast.makeText(context, R.string.not_found, LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        })
    }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.getParcelableExtra<WeatherDTO>(BUNDLE_KEY_WEATHER)?.let {
                setWeatherData(it)
            }
        }
    }

    private fun setWeatherData(weatherDTO: WeatherDTO?) {
        with(binding) {
            with(localWeather) {
                cityName.text = city.name
                cityCoordinates.text =
                    "${city.lat} ${city.lon}"
                temperatureValue.text = "${weatherDTO?.fact?.temp}"
                feelsLikeValue.text = "${weatherDTO?.fact?.feelsLike}"
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }
}
