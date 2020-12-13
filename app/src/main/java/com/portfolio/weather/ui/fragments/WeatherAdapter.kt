package com.portfolio.weather.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.weather.databinding.ItemWeatherDayBinding
import com.portfolio.weather.entity.local.DayWeatherItem
import com.portfolio.weather.entity.remote.WeatherByHour

class WeatherAdapter
    : ListAdapter<DayWeatherItem,WeatherAdapter.ItemViewHolder>(DIFF_UTIL) {

    companion object{
        val DIFF_UTIL = object : DiffUtil.ItemCallback<DayWeatherItem>() {
            override fun areItemsTheSame(oldItem: DayWeatherItem, newItem: DayWeatherItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DayWeatherItem,
                newItem: DayWeatherItem
            ): Boolean {
                return oldItem.date == newItem.date
            }

        }
    }

    inner class ItemViewHolder(private val binding: ItemWeatherDayBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DayWeatherItem){
            binding.model = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemWeatherDayBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}