package com.portfolio.weather.data.dataservice

import com.portfolio.weather.entity.remote.BaseResponse
import com.portfolio.weather.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApiService {
    
    @Headers("Content-Type: application/json")
    @GET("/data/2.5/forecast?appid=$API_KEY")
    suspend fun getWeather(
        @Query("lat") lat: Double = 33.441792,
        @Query("lon") lon: Double = -94.037689,
        @Query("units") units: String = "metric"
    ): Response<BaseResponse>

    @GET("/data/2.5/forecast?appid=$API_KEY")
    suspend fun getWeatherByCity(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
    ): Response<BaseResponse>

    @GET("/data/2.5/forecast?appid=$API_KEY")
    suspend fun getWeatherByCityName(
        @Query("q") city: String,
        @Query("units") units: String = "metric"
    ): Response<BaseResponse>


}