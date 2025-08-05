package com.group6.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
                val tempF = current.getString("temp_f")
                Log.d("tempF", "current temp set: $tempF")
                val url = current.getJSONObject("condition").getString("icon")
                Log.d("url", "icon url set: $url")
                val currCityWeather= CityWeather(location,"$tempF\u00B0F", url)
                cityWeatherList.add(currCityWeather)
                val adapter = WeatherAdapter(cityWeatherList, object : WeatherAdapter.OnItemLongClickListener {
                    override fun onItemLongClick(position: Int) {
                        val item = cityWeatherList[position]
                        Toast.makeText(this@MainActivity, "Long clicked: ${item.city}", Toast.LENGTH_SHORT).show()
                    }
                })
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