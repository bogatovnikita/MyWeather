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
import com.bogatovnikita.myweather.model.Weather
import com.bogatovnikita.myweather.view.details.BUNDLE_KEY
import com.bogatovnikita.myweather.view.details.DetailsFragment
import com.bogatovnikita.myweather.viewmodel.AppState
import com.bogatovnikita.myweather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(), OnMyItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private lateinit var viewModel: MainViewModel
    private val adapter = MainFragmentAdapter(this)
    private var isRussian = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //провайдер создает только один экземляр конкретного хранилища, чтобы избежать утечки памяти
        //observer подписывает объект на обновление данных в livedata, отслеживает жизненый цикл фрагмента
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })

        binding.mainFragmentRecyclerView.adapter = adapter

        viewModel.getWeatherFromLocalSourceRus()

        binding.mainFragmentFAB.setOnClickListener {
            sentRequest()
        }
    }

    private fun sentRequest() {
        isRussian = !isRussian
        if (isRussian) {
            viewModel.getWeatherFromLocalSourceRus()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        } else {
            viewModel.getWeatherFromLocalSourceWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar.make(binding.root, R.string.error, Snackbar.LENGTH_LONG)
                    .setAction(R.string.try_again) {
                        sentRequest()
                    }.show()
            }
            is AppState.Loading -> binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(appState.weatherData)
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

    override fun onItemClick(weather: Weather) {
        val bundle = Bundle()
        bundle.putParcelable(BUNDLE_KEY, weather)
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.main_activity_container, DetailsFragment.newInstance(bundle))
            .addToBackStack("").commit()
    }
}