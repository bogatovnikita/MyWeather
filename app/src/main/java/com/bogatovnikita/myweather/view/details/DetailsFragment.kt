package com.bogatovnikita.myweather.view.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bogatovnikita.myweather.R
import com.bogatovnikita.myweather.databinding.FragmentDetailsBinding
import com.bogatovnikita.myweather.model.Weather
import com.bogatovnikita.myweather.model.WeatherDTO
import com.bogatovnikita.myweather.utils.WeatherLoader
import com.google.android.material.snackbar.Snackbar

const val BUNDLE_KEY = "key"

class DetailsFragment : Fragment(), WeatherLoader.OnWeatherLoader {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    private val weatherLoader = WeatherLoader(this)
    lateinit var localWeather: Weather

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            it.getParcelable<Weather>(BUNDLE_KEY)?.let {
                localWeather = it
                weatherLoader.loadWeather(it.city.lat, it.city.lon)
            }
        }
    }

    private fun setWeatherData(weatherDTO: WeatherDTO) {
        with(binding) {
            with(localWeather) {
                cityName.text = city.name
                cityCoordinates.text =
                    "${city.lat} ${city.lon}"
                temperatureValue.text = "${weatherDTO.fact.temp}"
                feelsLikeValue.text = "${weatherDTO.fact.feelsLike}"
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
    }

    override fun onLoaded(weatherDTO: WeatherDTO?) {
        weatherDTO?.let {
            setWeatherData(weatherDTO)
        }
    }

    override fun onFailed(weatherDTO: WeatherDTO) {
        Snackbar.make(requireView(), R.string.error, Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again) {
                View.OnClickListener { onLoaded(weatherDTO) }
            }.show()
    }
}
