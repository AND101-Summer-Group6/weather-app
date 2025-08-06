package com.group6.weatherapp

data class CityWeather(
    val city: String,
    val temperature: Double,
    val condition: String,
    val conditionIcon: String,
    val humidity: Int
)
