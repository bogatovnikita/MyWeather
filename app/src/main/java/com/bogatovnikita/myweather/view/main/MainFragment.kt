package com.bogatovnikita.myweather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bogatovnikita.myweather.R
import com.bogatovnikita.myweather.databinding.FragmentMainBinding
import com.bogatovnikita.myweather.viewmodel.AppState
import com.bogatovnikita.myweather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //провайдер создает только один экземляр конкретного хранилища, чтобы избежать утечки памяти
        //observer подписывает объект на обновление данных в livedata, отслеживает жизненый цикл фрагмента
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
        viewModel.getWeatherFromServer()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainView, R.string.error, Snackbar.LENGTH_LONG)
                    .setAction(R.string.try_again) {
                        viewModel.getWeatherFromServer()
                    }.show()
            }
            is AppState.Loading -> binding.loadingLayout.visibility = View.VISIBLE
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.cityName.text = appState.weatherData.city.name
                binding.cityCoordinates.text =
                    "${appState.weatherData.city.lat} ${appState.weatherData.city.lon}"
                binding.temperatureValue.text = appState.weatherData.temperature.toString()
                binding.feelsLikeValue.text = appState.weatherData.feelsLike.toString()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}