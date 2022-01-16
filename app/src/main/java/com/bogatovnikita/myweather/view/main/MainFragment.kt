package com.bogatovnikita.myweather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bogatovnikita.myweather.BUNDLE_KEY
import com.bogatovnikita.myweather.R
import com.bogatovnikita.myweather.databinding.FragmentMainBinding
import com.bogatovnikita.myweather.model.Weather
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

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    //провайдер создает только один экземляр конкретного хранилища, чтобы избежать утечки памяти

    private val adapter: MainFragmentAdapter by lazy { MainFragmentAdapter(this) }
    private var isRussian = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        //observer подписывает объект на обновление данных в livedata, отслеживает жизненый цикл фрагмента
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun initView() {
        with(binding) {
            mainFragmentFAB.setOnClickListener {
                sentRequest()
            }
            mainFragmentRecyclerView.adapter = adapter
        }
    }

    private fun sentRequest() {
        isRussian = !isRussian
        with(binding) {
            with(viewModel) {
                if (isRussian) {
                    getWeatherFromLocalSourceRus()
                    mainFragmentFAB.setImageResource(R.drawable.ic_russia)
                } else {
                    getWeatherFromLocalSourceWorld()
                    mainFragmentFAB.setImageResource(R.drawable.ic_earth)
                }
            }
        }
    }

    private fun renderData(appState: AppState) {
        with(binding) {
            when (appState) {
                is AppState.Error -> {
                    mainFragmentLoadingLayout.visibility = View.GONE
                    root.setWithoutAction(
                        (R.string.error),
                        (R.string.try_again),
                        { sentRequest() },
                        Snackbar.LENGTH_LONG
                    )
                }
                is AppState.Loading -> mainFragmentLoadingLayout.visibility = View.VISIBLE
                is AppState.Success -> {
                    mainFragmentLoadingLayout.visibility = View.GONE
                    adapter.setWeather(appState.weatherData)
                    root.withoutAction(R.string.success, Snackbar.LENGTH_LONG)
                }
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
        activity?.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_activity_container, DetailsFragment.newInstance(Bundle().apply {
                    putParcelable(BUNDLE_KEY, weather)
                }))
                .addToBackStack("").commit()
        }
    }

    private fun View.withoutAction(text: Int, leinghtShow: Int) {
        Snackbar.make(this, text, leinghtShow).show()
    }

    private fun View.setWithoutAction(
        text: Int,
        actionText: Int,
        action: (View) -> Unit,
        leinghtShow: Int
    ) {
        Snackbar.make(this, text, leinghtShow).setAction(actionText, action).show()
    }
}