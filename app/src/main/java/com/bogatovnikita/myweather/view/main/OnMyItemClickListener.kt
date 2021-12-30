package com.bogatovnikita.myweather.view.main

import com.bogatovnikita.myweather.model.Weather

interface OnMyItemClickListener {
    fun onItemClick(weather: Weather)
}