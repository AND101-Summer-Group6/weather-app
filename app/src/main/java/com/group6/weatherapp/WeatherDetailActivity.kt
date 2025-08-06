package com.group6.weatherapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.group6.weatherapp.databinding.ActivityWeatherDetailBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class WeatherDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityWeatherDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val currCityWeather = intent.getSerializableExtra("theCityWeather") as? CityWeather
        if (currCityWeather != null) {
            val forecastList=currCityWeather.forecast
            Glide.with(this)
                .load(currCityWeather.conditionIcon)
                .fitCenter()
                .into(binding.imageDetailCondition)
            binding.textviewCityName.text=currCityWeather.city
            binding.textviewCurrTime.text=getCurrentTimeInUTCFormatted()
            binding.textviewTemp.text=this.getString(R.string.temperature_with_text_format, currCityWeather.temperature)
            binding.textviewPrecip.text=this.getString(R.string.precipitation_format,currCityWeather.precipitation)
            binding.textviewHumidity.text=this.getString(R.string.humidity_format,currCityWeather.humidity)
            binding.textviewWind.text=applicationContext.getString(R.string.wind_format,currCityWeather.wind)
            binding.listForecast.adapter= WeatherDetailAdapter(forecastList)
            binding.listForecast.layoutManager = LinearLayoutManager(this@WeatherDetailActivity,LinearLayoutManager.HORIZONTAL, false)
            binding.btnBack.setOnClickListener {
                finish()
            }
        } else {
            finish()
        }
    }

    fun getCurrentTimeInUTCFormatted(): String {
        val date = Date()
        val sdf = SimpleDateFormat("EEEE HH:mm 'UTC'", Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(date)
    }

}