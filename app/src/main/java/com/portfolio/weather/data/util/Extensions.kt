package com.portfolio.weather.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.portfolio.weather.entity.local.CallException
import com.portfolio.weather.entity.local.Result
import retrofit2.Response


suspend fun <R> makeApiCall(
    call: suspend () -> Result<R>,
    errorMessage: Int = 4444
) = try {
    call()
} catch (e: Exception) {
    Result.Error(CallException<Nothing>(errorMessage))
}

fun <R> analyzeResponse(
    response: Response<R>
): Result<R> {
    return if(response.isSuccessful){
        Result.Success(response.body())
    }else{
        Result.Error(CallException<Nothing>(response.code(), response.message()))
    }
}

fun Context.hasNetwork(): Boolean{
    var isConnected = false
    val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        isConnected = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            @Suppress("DEPRECATION")
            connectivityManager.activeNetworkInfo?.run {
                isConnected = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }
    return isConnected
}

