package com.group6.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherDetailAdapter(private val forecastList: List<ForecastWeather>) : RecyclerView.Adapter<WeatherDetailAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Find our RecyclerView item's ImageView for future use
        val weekday: TextView = view.findViewById(R.id.text_forecast_date)
        val weatherIcon: ImageView = view.findViewById(R.id.image_forecast_condition)
        val temp: TextView = view.findViewById(R.id.text_forecast_temp)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)

        return ViewHolder(view)
    }
    override fun getItemCount() = forecastList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecastDay = forecastList[position]
//        Log.d("Paul",getDayOfWeekFromEpoch(forecastDay.dateEpoch))
        holder.weekday.text = getDayOfWeekFromEpoch(forecastDay.dateEpoch)
        val formatted = holder.itemView.context.getString(R.string.temperature_min_max_format, forecastDay.minTemp,forecastDay.maxTemp)
        holder.temp.text = formatted
        Glide.with(holder.itemView.context)
            .load(forecastDay.conditionIconUrl)
            .fitCenter()
            .into(holder.weatherIcon)
    }

    fun getDayOfWeekFromEpoch(epochSeconds: Long): String {
        val date = Date(epochSeconds * 1000)
        val sdf = SimpleDateFormat("EEE", Locale.ENGLISH)
        return sdf.format(date)
    }
}