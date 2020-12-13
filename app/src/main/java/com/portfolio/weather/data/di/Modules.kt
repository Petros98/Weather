package com.portfolio.weather.data.di

import com.portfolio.weather.data.dataservice.WeatherApiService
import com.portfolio.weather.data.dataservice.WeatherRepository
import com.portfolio.weather.data.repository.WeatherRepositoryImpl
import com.portfolio.weather.data.util.ConnectivityInterceptor
import com.portfolio.weather.utils.Constants
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val apiModule = module {
    single { Moshi.Builder().build() }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .apply {
                    client(
                            OkHttpClient.Builder()
                                    //for data caching
                                    .cache(Cache(androidContext().cacheDir,(10 *1024 * 1024).toLong()))
                                    .addInterceptor(ConnectivityInterceptor(get()))
                                    .addInterceptor(HttpLoggingInterceptor().apply {
                                        level = HttpLoggingInterceptor.Level.BODY
                                    })
                                    .build()
                    )
                }
                .build()
    }
    single<WeatherApiService> { get<Retrofit>().create(WeatherApiService::class.java) }

}

val repositoryModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}