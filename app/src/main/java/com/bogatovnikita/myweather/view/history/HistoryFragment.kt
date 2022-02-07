package com.bogatovnikita.myweather.view.history

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bogatovnikita.myweather.databinding.FragmentHistoryBinding
import com.bogatovnikita.myweather.view.BaseFragment
import com.bogatovnikita.myweather.viewmodel.AppState
import com.bogatovnikita.myweather.viewmodel.HistoryViewModel

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate),
    AdapterView.OnItemClickListener {

    private val adapter: CitiesHistoryAdapter by lazy {
        CitiesHistoryAdapter(this)
    }

    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> {
            renderData(it)
        })
        viewModel.getAllHistory()
        binding.historyFragmentRecyclerview.adapter = adapter
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                adapter.setWeather(appState.weatherData)
            }
        }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }
}