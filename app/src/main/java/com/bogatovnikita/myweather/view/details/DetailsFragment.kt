package com.bogatovnikita.myweather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import com.bogatovnikita.myweather.BUNDLE_KEY
import com.bogatovnikita.myweather.R
import com.bogatovnikita.myweather.databinding.FragmentDetailsBinding
import com.bogatovnikita.myweather.model.Weather
import com.bogatovnikita.myweather.viewmodel.AppState
import com.bogatovnikita.myweather.viewmodel.DetailsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private lateinit var localWeather: Weather
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    companion object {
        fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        arguments?.let {
            it.getParcelable<Weather>(BUNDLE_KEY)?.let {
                localWeather = it
                viewModel.getWeatherFromServer(localWeather.city.lat, localWeather.city.lon)
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

    private fun renderData(appState: AppState) {
        with(binding) {
            when (appState) {
                is AppState.Error -> {
                    loadingLayout.visibility = View.GONE
                    root.withoutAction(R.string.error, Snackbar.LENGTH_LONG)
                }
                is AppState.Loading -> {
                }
                is AppState.Success -> {
                    val weather = appState.weatherData[0]
                    setWeatherData(weather)
                }
            }
        }
    }

    private fun setWeatherData(weather: Weather) {
        with(binding) {
            with(localWeather) {
                cityName.text = city.name
                cityCoordinates.text =
                    "${city.lat} ${city.lon}"
                temperatureValue.text = "${weather.temperature}"
                feelsLikeValue.text = "${weather.feelsLike}"
            }

        }

        headerIcon.load(R.drawable.city_pictures)
        weatherIcon.loadUrl("https://yastatic.net/weather/i/icons/funky/dark/${weather.icon}.svg")
    }

    private fun View.withoutAction(text: Int, leinghtShow: Int) {
        Snackbar.make(this, text, leinghtShow).show()
    }

    private fun ImageView.loadUrl(url: String) {

        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
