package com.bogatovnikita.myweather.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bogatovnikita.myweather.*
import com.bogatovnikita.myweather.databinding.FragmentDetailsBinding
import com.bogatovnikita.myweather.model.Weather
import com.bogatovnikita.myweather.model.WeatherDTO
import com.bogatovnikita.myweather.utils.WeatherLoader
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment(), WeatherLoader.OnWeatherLoader {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.getParcelableExtra<WeatherDTO>(BUNDLE_KEY_WEATHER)?.let {
                setWeatherData(it)
            }
        }
    }

    private lateinit var localWeather: Weather

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            it.getParcelable<Weather>(BUNDLE_KEY)?.let {
                localWeather = it
                requireActivity().startService(
                    Intent(
                        requireActivity(),
                        DetailsIntentService::class.java
                    ).apply {
                        putExtra(BUNDLE_KEY_LAT, it.city.lat)
                        putExtra(BUNDLE_KEY_LON, it.city.lon)
                    })
            }
        }
        requireActivity().registerReceiver(receiver, IntentFilter(BROADCAST_INTENT_KEY))
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        requireActivity().unregisterReceiver(receiver)
    }

    override fun onLoaded(weatherDTO: WeatherDTO?) {
        weatherDTO?.let {
            setWeatherData(weatherDTO)
        }
    }

    override fun onFailed() {
        Snackbar.make(requireView(), R.string.error, Snackbar.LENGTH_LONG).show()
    }
}
