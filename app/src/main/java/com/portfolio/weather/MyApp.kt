package com.portfolio.weather

import android.app.Application
import com.portfolio.weather.data.di.apiModule
import com.portfolio.weather.data.di.repositoryModule
import com.portfolio.weather.domain.di.interactorsModule
import com.portfolio.weather.ui.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(modules)
        }
    }
    private val modules = listOf(
        apiModule,
        repositoryModule,
        interactorsModule,
        viewModule
    )
    
}