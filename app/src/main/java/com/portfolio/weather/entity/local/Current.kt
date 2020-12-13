package com.portfolio.weather.entity.local

import com.portfolio.weather.entity.remote.City

data class Current(
    val dateText: String,
    val feelsLike: String,
    val humidity: String,
    val pressure: Double,
    val temp: String,
    val visibility: Double,
    val daysWeather: List<DayWeatherItem>,
    val city: City
)