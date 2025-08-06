package com.group6.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.group6.weatherapp.databinding.ActivityMainBinding
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    val key = BuildConfig.apiKeySafe
    private lateinit var cityList : MutableList<String>
    private lateinit var cityWeatherList : MutableList<CityWeather>
    private lateinit var rvWeather : RecyclerView

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cityList = mutableListOf("London", "New York", "Paris", "Tokyo", "Bangkok", "Chicago", "Los Angeles", "San Diego")
        cityWeatherList = mutableListOf()
        rvWeather = binding.weatherList
        for (city in cityList) {
            fetchWeather(city)
        }

    }
    private fun fetchWeather(location: String) {
        val client = AsyncHttpClient()
        client["https://api.weatherapi.com/v1/forecast.json?key=$key&q=$location&days=7", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("Weather", "response successful: $json")
                val current = json.jsonObject.getJSONObject("current")
                val tempF = current.getDouble("temp_f")
                val condition=current.getJSONObject("condition").getString("text")
                val conditionIconUrl=applicationContext.getString(R.string.icon_url_format,current.getJSONObject("condition").getString("icon"))
                val humidity=current.getInt("humidity")
                val currCityWeather= CityWeather(location,tempF,condition,conditionIconUrl,humidity)
                cityWeatherList.add(currCityWeather)
                val adapter = WeatherAdapter(cityWeatherList)
                rvWeather.adapter = adapter
                rvWeather.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Weather Error", errorResponse)
            }
        }]
    }

}