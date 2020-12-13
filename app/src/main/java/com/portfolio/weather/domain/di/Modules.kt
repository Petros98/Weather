package com.portfolio.weather.domain.di

import com.portfolio.weather.domain.interactors.WeatherInteractor
import com.portfolio.weather.domain.usecase.WeatherUseCase
import org.koin.dsl.module

val interactorsModule = module {
    single<WeatherInteractor> { WeatherUseCase(get()) }
}