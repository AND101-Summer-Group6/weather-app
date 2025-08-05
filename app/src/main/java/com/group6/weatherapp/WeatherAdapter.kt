package com.group6.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WeatherAdapter(private val weatherList: List<CityWeather>, private val longClickListener: OnItemLongClickListener) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityName: TextView
        val cityTemperature: TextView
        val weatherIcon: ImageView

        init {
            // Find our RecyclerView item's ImageView for future use
            cityName = view.findViewById(R.id.city_name)
            cityTemperature = view.findViewById(R.id.temperature)
            weatherIcon = view.findViewById(R.id.weather_icon)
            view.setOnLongClickListener {
                val position = getBindingAdapterPosition()
                if (position != RecyclerView.NO_POSITION) {
                    longClickListener.onItemLongClick(position)
                }
                true // <- return true to indicate the event is consumed
            }
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
        Glide.with(holder.itemView)
            .load("https:${weather.iconURL}")
            .fitCenter()
            .into(holder.weatherIcon)
        holder.cityName.text = weather.city
        holder.cityTemperature.text = weather.temperature
    }
}