package com.portfolio.weather.utils

import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.portfolio.weather.R
import com.portfolio.weather.entity.local.DayWeatherItem
import com.portfolio.weather.entity.remote.City
import com.portfolio.weather.ui.fragments.WeatherAdapter

@BindingAdapter("items_list")
fun Spinner.bindAdapter(cities: List<City>?){
    cities?.let { list ->
        this.adapter = context?.let {
            ArrayAdapter(it, R.layout.item_city,list).apply {
                setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            }
        }
    }

}

@BindingAdapter("string_items")
fun MaterialAutoCompleteTextView.bindAdapter(cities: List<City>?){
    cities?.let { list ->
        this.setAdapter(context?.let {
            ArrayAdapter(it, R.layout.item_city,list).apply {
                setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            }
        })
    }
}

@BindingAdapter("logo")
fun ImageView.bindImage(logo: String?){
    Glide.with(this)
        .load("http://openweathermap.org/img/wn/$logo@2x.png")
        .into(this)
}

@BindingAdapter("list_data")
fun RecyclerView.bindList(list: List<DayWeatherItem>?){
    if (this.adapter == null)
        adapter = WeatherAdapter().apply { submitList(list) }
    else if(this.adapter is WeatherAdapter)
        (adapter as WeatherAdapter).submitList(list)
}