package com.portfolio.weather.data.repository

import com.portfolio.weather.data.dataservice.WeatherApiService
import com.portfolio.weather.data.dataservice.WeatherRepository
import com.portfolio.weather.data.util.analyzeResponse
import com.portfolio.weather.data.util.makeApiCall
import com.portfolio.weather.entity.local.Result
import com.portfolio.weather.entity.remote.BaseResponse

class WeatherRepositoryImpl(
    private val api: WeatherApiService
) : WeatherRepository {

    override suspend fun getWeather(): Result<BaseResponse> =
        makeApiCall({ analyzeResponse(api.getWeather()) })

    override suspend fun getWeatherByCoordinates(lat: Double, lon: Double): Result<BaseResponse> =
        makeApiCall({analyzeResponse(api.getWeatherByCity(lat, lon))})

    override suspend fun getWeatherByName(cityName: String): Result<BaseResponse> =
        makeApiCall({ analyzeResponse(api.getWeatherByCityName(cityName))})

    override suspend fun getByLocation(latitude: Double, longitude: Double): Result<BaseResponse> =
        makeApiCall({ analyzeResponse(api.getWeather(latitude,longitude))})

}