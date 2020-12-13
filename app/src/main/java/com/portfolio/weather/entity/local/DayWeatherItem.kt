package com.portfolio.weather.entity.local

data class DayWeatherItem(
    val day: Int,
    val month: Int,
    val date: String,
    val feelsLike: String,
    var humidity: String,
    val pressure: Double,
    val temp: String,
    val tempMax: String,
    val tempMin: String,
    val description: String,
    val icon: String
)
