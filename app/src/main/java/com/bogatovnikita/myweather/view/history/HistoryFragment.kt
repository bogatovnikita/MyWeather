package com.bogatovnikita.myweather.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bogatovnikita.myweather.databinding.FragmentHistoryBinding
import com.bogatovnikita.myweather.viewmodel.AppState
import com.bogatovnikita.myweather.viewmodel.HistoryViewModel

class HistoryFragment : Fragment(), AdapterView.OnItemClickListener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding: FragmentHistoryBinding
        get() {
            return _binding!!
        }

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}