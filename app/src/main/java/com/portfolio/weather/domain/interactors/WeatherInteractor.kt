package com.portfolio.weather.domain.interactors

import android.location.Location
import com.portfolio.weather.entity.local.Current
import com.portfolio.weather.entity.local.Result

interface WeatherInteractor {
    suspend fun getWeather() : Result<Current>
    suspend fun getWeatherByCityName(cityName: String) : Result<Current>
    suspend fun getByLocation(location: Location): Result<Current>
}