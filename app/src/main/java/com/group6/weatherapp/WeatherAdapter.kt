package com.group6.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WeatherAdapter(private val weatherList: List<CityWeather>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityName: TextView
        val cityTemperature: TextView

        val cityConditionIcon: ImageView

        val cityCondition: TextView

        val cityHumidity: TextView

        init {
            // Find our RecyclerView item's ImageView for future use
            cityName = view.findViewById(R.id.city_name)
            cityTemperature = view.findViewById(R.id.temperature)
            cityConditionIcon=view.findViewById(R.id.image_condition_icon)
            cityCondition=view.findViewById(R.id.textview_condition)
            cityHumidity=view.findViewById<TextView>(R.id.textview_humidity)
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
        val temp = weather.temperature
        val formatted = holder.itemView.context.getString(R.string.temperature_format, temp)
        holder.cityTemperature.text = formatted
        holder.cityCondition.text=weather.condition
        holder.cityHumidity.text=holder.itemView.context.getString(R.string.humidity_format,weather.humidity)
        Glide.with(holder.itemView.context)
            .load(weather.conditionIcon)
            .fitCenter()
            .into(holder.cityConditionIcon)
        holder.itemView.setOnLongClickListener {
            Toast.makeText(holder.itemView.context, "You long-clicked ${weather.city}", Toast.LENGTH_SHORT).show()
            true
        }
    }
}