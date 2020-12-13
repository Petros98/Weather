package com.portfolio.weather.entity.remote

import com.squareup.moshi.Json

data class WeatherByHour(
    @field:Json(name = "dt")
    val dt: Double,
    @field:Json(name = "dt_txt")
    val dtTxt: String,
    @field:Json(name = "main")
    val main: Main,
    @field:Json(name = "visibility")
    val visibility: Double,
    @field:Json(name = "weather")
    val weather: List<Weather>,
)