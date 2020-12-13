package com.portfolio.weather.entity.remote


import com.squareup.moshi.Json

data class BaseResponse(
    @field:Json(name = "city")
    val city: City,
    @field:Json(name = "cnt")
    val cnt: Int,
    @field:Json(name = "cod")
    val cod: String,
    @field:Json(name = "list")
    val list: List<WeatherByHour>,
    @field:Json(name = "message")
    val message: Double
)