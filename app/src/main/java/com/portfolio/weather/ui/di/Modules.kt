package com.portfolio.weather.ui.di

import com.portfolio.weather.ui.fragments.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel { WeatherViewModel(get()) }
}