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
        //cityList = mutableListOf("London", "New York", "Paris", "Tokyo", "Bangkok", "Chicago", "Los Angeles", "San Diego")
        cityList = mutableListOf(
            "New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia",
            "San Antonio", "San Diego", "Dallas", "San Jose", "Austin", "Jacksonville",
            "Fort Worth", "Columbus", "Charlotte", "San Francisco", "Indianapolis",
            "Seattle", "Denver", "Washington", "Boston", "Nashville", "Baltimore",
            "Oklahoma City", "Louisville", "Portland", "Las Vegas", "Memphis", "Detroit",
            "Toronto", "Montreal", "Vancouver", "Calgary", "Edmonton", "Ottawa",
            "Mexico City", "Monterrey", "Puebla", "Tijuana", "León",
            "London", "Berlin", "Madrid", "Rome", "Paris", "Amsterdam", "Vienna",
            "Prague", "Budapest", "Warsaw", "Stockholm", "Copenhagen", "Oslo",
            "Helsinki", "Dublin", "Lisbon", "Athens", "Brussels", "Zurich",
            "Barcelona", "Milan", "Naples", "Hamburg", "Munich", "Cologne",
            "Frankfurt", "Stuttgart", "Manchester", "Birmingham", "Liverpool",
            "Edinburgh", "Glasgow", "Cardiff", "Belfast",
            "Tokyo", "Delhi", "Shanghai", "Dhaka", "São Paulo", "Cairo", "Mexico City",
            "Beijing", "Mumbai", "Osaka", "Karachi", "Chongqing", "Istanbul", "Buenos Aires",
            "Kolkata", "Manila", "Lagos", "Rio de Janeiro", "Tianjin", "Kinshasa"
        )
        cityWeatherList = mutableListOf()
        rvWeather = binding.weatherList
        for (city in cityList) {
            fetchWeather(city)
        }

    }
    private fun fetchWeather(location: String) {
        val client = AsyncHttpClient()
        client["https://api.weatherapi.com/v1/forecast.json?key=$key&q=$location&days=6", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("Weather", "response successful: $json")

                val current = json.jsonObject.getJSONObject("current")
                val tempF = current.getDouble("temp_f")
                val condition=current.getJSONObject("condition").getString("text")
                val conditionIconUrl=applicationContext.getString(R.string.icon_url_format,current.getJSONObject("condition").getString("icon"))
                val humidity=current.getInt("humidity")
                val precipitation=current.getDouble("precip_mm")
                val wind=current.getDouble("wind_mph")

                val forecastList = mutableListOf<ForecastWeather>()
                val forecastDays = json.jsonObject.getJSONObject("forecast").getJSONArray("forecastday")

                for (i in 0 until forecastDays.length()) {
                    val dayObj = forecastDays.getJSONObject(i)
                    val dateEpoch = dayObj.getLong("date_epoch")
                    val dayInfo = dayObj.getJSONObject("day")
                    val maxTempF = dayInfo.getDouble("maxtemp_f")
                    val minTempF = dayInfo.getDouble("mintemp_f")
                    val iconPathForecast = dayInfo.getJSONObject("condition").getString("icon")
                    val iconUrlForecast = applicationContext.getString(R.string.icon_url_format, iconPathForecast)

                    forecastList.add(ForecastWeather(dateEpoch,maxTempF,minTempF,iconUrlForecast))
                }

                val currCityWeather= CityWeather(location,tempF,condition,conditionIconUrl,humidity,precipitation,wind,forecastList)
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