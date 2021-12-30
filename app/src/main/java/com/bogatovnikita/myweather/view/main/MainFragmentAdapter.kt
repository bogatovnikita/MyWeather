package com.bogatovnikita.myweather.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bogatovnikita.myweather.R
import com.bogatovnikita.myweather.model.Weather

class MainFragmentAdapter : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var weatherData: List<Weather> = listOf()

    fun setWeather(data: List<Weather>) {
        this.weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainFragmentAdapter.MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.main_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainFragmentAdapter.MainViewHolder, position: Int) {
        return holder.bind(this.weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) {
            itemView.findViewById<AppCompatTextView>(R.id.mainFragmentRecyclerItemTextView).text =
                weather.city.name
            itemView.setOnClickListener {}
        }
    }
}