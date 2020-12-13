package com.portfolio.weather.data.dataservice

import com.portfolio.weather.entity.local.Result
import com.portfolio.weather.entity.remote.BaseResponse

interface WeatherRepository {

    suspend fun getWeather() : Result<BaseResponse>
    suspend fun getWeatherByCoordinates(lat: Double, lon: Double): Result<BaseResponse>
    suspend fun getWeatherByName(cityName: String) : Result<BaseResponse>
    suspend fun getByLocation(latitude: Double, longitude: Double): Result<BaseResponse>
}