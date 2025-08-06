package com.group6.weatherapp

import java.io.Serializable

data class CityWeather(
    val city: String,
    val temperature: Double,
    val condition: String,
    val conditionIcon: String,
    val humidity: Int,
    val precipitation: Double,
    val wind: Double,
    val forecast: List<ForecastWeather>
): Serializable
