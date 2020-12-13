package com.portfolio.weather.entity.local

data class City(
    val name: String,
    val lon: Double,
    val lat: Double
){
    override fun toString(): String = name
}
