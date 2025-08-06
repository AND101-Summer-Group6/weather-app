package com.group6.weatherapp

import java.io.Serializable

data class ForecastWeather(
    val dateEpoch: Long,
    val maxTemp: Double,
    val minTemp: Double,
    val conditionIconUrl: String
): Serializable
