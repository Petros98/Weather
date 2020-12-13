package com.portfolio.weather.utils

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.portfolio.weather.data.util.hasNetwork

class ConnectivityReceiver(private val onChange: (Boolean) -> (Unit)) : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        onChange.invoke(context.hasNetwork())
    }
}