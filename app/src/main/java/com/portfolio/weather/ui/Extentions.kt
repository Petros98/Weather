package com.portfolio.weather.ui

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackbar(text: String){
    view?.let {
        Snackbar.make(it,text,Snackbar.LENGTH_SHORT).show()
    }
}