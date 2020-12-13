package com.portfolio.weather.domain

import com.portfolio.weather.entity.local.Current
import com.portfolio.weather.entity.local.DayWeatherItem
import com.portfolio.weather.entity.remote.BaseResponse
import com.portfolio.weather.entity.remote.WeatherByHour

fun BaseResponse.toCurrentObject() : Current{
    list[0].let {
        return Current(
                dateText = it.dtTxt.substringBefore(" "),
                feelsLike = "${it.main.feelsLike.substringBefore(".")}°C",
                humidity = "${it.main.humidity}%",
                pressure = it.main.pressure,
                temp = "${it.main.temp.substringBefore(".")}°C",
                visibility = it.visibility,
                daysWeather = list.toDayItemList(),
                city = this.city
        )
    }

}

fun WeatherByHour.toDayWeatherItem(): DayWeatherItem{
    return DayWeatherItem(
            date = dtTxt.substringAfter("-").substringBefore(" ").replace("-","/",false),
            tempMax = "${main.tempMax.substringBefore(".")}°C",
            tempMin = "${main.tempMin.substringBefore(".")}°C",
            description = weather[0].description,
            icon = weather[0].icon,
            feelsLike = "${main.feelsLike.substringBefore(".")}°C",
            humidity = "${main.humidity}%",
            pressure = main.pressure,
            temp = "${main.temp.substringBefore(".")}°C",
            day = dtTxt.substringBefore(" ").substringAfterLast("-").toIntOrNull() ?: 0,
            month = dtTxt.substringAfter("-").substringBefore("-").toIntOrNull() ?: 0
    )
}

fun List<WeatherByHour>.toDayItemList() : List<DayWeatherItem>{
    var day = 0
    val list = mutableListOf<DayWeatherItem>()
    forEach {
        val item = it.toDayWeatherItem()
        if (day == 0){
            day = item.day
            list.add(item)
        }
        else if (item.day>day){
            day = item.day
            list.add(item)
        }
    }
    return list
}