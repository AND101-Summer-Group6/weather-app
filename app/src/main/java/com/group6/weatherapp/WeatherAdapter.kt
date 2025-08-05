package com.group6.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeatherAdapter(private val weatherList: List<CityWeather>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityName: TextView
        val cityTemperature: TextView

        init {
            // Find our RecyclerView item's ImageView for future use
            cityName = view.findViewById(R.id.city_name)
            cityTemperature = view.findViewById(R.id.temperature)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)

        return ViewHolder(view)
    }
    override fun getItemCount() = weatherList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.cityName.text = weather.city
        holder.cityTemperature.text = weather.temperature
    }
}