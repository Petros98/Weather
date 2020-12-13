package com.portfolio.weather.entity.remote


import com.squareup.moshi.Json

data class Main(
    val date: String = "",
    @field:Json(name = "feels_like")
    val feelsLike: String,
    @field:Json(name = "grnd_level")
    val grndLevel: Int,
    @field:Json(name = "humidity")
    var humidity: String,
    @field:Json(name = "pressure")
    val pressure: Double,
    @field:Json(name = "sea_level")
    val seaLevel: Int,
    @field:Json(name = "temp")
    val temp: String,
    @field:Json(name = "temp_max")
    val tempMax: String,
    @field:Json(name = "temp_min")
    val tempMin: String
)