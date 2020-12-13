package com.portfolio.weather.entity.remote


import com.squareup.moshi.Json

data class City(
    @field:Json(name = "coord")
    val coord: Coord,
    @field:Json(name = "country")
    val country: String,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "population")
    val population: Int,
    @field:Json(name = "sunrise")
    val sunrise: Int,
    @field:Json(name = "sunset")
    val sunset: Int,
    @field:Json(name = "timezone")
    val timezone: Int
){
    override fun toString(): String {
        return name
    }
}