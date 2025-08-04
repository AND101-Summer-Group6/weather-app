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
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    val key = ""
    private lateinit var cityList : MutableList<String>
    private lateinit var cityTempList : MutableList<String>
    private lateinit var rvWeather : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cityList = mutableListOf("London", "New York", "Paris", "Tokyo", "Bangkok", "Chicago", "Los Angeles", "San Diego")
        cityTempList = mutableListOf()
        rvWeather = findViewById(R.id.weather_list)
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
                cityTempList.add("$tempF\u00B0F")
                val adapter = WeatherAdapter(cityList, cityTempList)
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