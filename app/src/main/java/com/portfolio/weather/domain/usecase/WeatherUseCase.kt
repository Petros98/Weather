package com.portfolio.weather.domain.usecase

import android.location.Location
import com.portfolio.weather.data.dataservice.WeatherRepository
import com.portfolio.weather.domain.interactors.WeatherInteractor
import com.portfolio.weather.domain.toCurrentObject
import com.portfolio.weather.entity.local.CallException
import com.portfolio.weather.entity.local.Current
import com.portfolio.weather.entity.local.Result
import com.portfolio.weather.utils.Constants.ERROR_NULL_DATA

class WeatherUseCase(
    private val repository: WeatherRepository
) : WeatherInteractor {

    override suspend fun getWeather(): Result<Current> {
        return when(val data= repository.getWeather()){
            is Result.Success->{
                data.data?.let {
                    Result.Success(it.toCurrentObject())
                }?: Result.Error(
                    CallException(
                        ERROR_NULL_DATA,
                        "Data is null"
                    )
                )

            }
            else ->  Result.Error(
                CallException(
                    ERROR_NULL_DATA,
                    "Error call"
                )
            )
        }
    }

    override suspend fun getWeatherByCityName(cityName: String): Result<Current> {
        return when(val data= repository.getWeatherByName(cityName)){
            is Result.Success->{
                data.data?.let {
                    Result.Success(it.toCurrentObject())
                }?: Result.Error(
                    CallException(
                        ERROR_NULL_DATA,
                        "Data is null"
                    )
                )

            }
            else ->  Result.Error(
                CallException(
                    ERROR_NULL_DATA,
                    "Not found"
                )
            )
        }
    }

    override suspend fun getByLocation(location: Location): Result<Current> {
        return when(val data= repository.getByLocation(location.latitude,location.longitude)){
            is Result.Success->{
                data.data?.let {
                    Result.Success(it.toCurrentObject())
                }?: Result.Error(
                    CallException(
                        ERROR_NULL_DATA,
                        "Data is null"
                    )
                )

            }
            else ->  Result.Error(
                CallException(
                    ERROR_NULL_DATA,
                    "Not found"
                )
            )
        }
    }
}