package com.portfolio.weather.data.util

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ConnectivityInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = if (context.hasNetwork())
            request.newBuilder().header("Cache-Control", "public, max-age=" + 10).build()
        else
            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
        return chain.proceed(request)
    }

}