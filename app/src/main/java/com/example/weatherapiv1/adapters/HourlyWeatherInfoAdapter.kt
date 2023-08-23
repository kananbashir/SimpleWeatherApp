package com.example.weatherapiv1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapiv1.data.db.entities.weather.HourlyInfo
import com.example.weatherapiv1.databinding.WeatherInfoHourlyItemLayoutBinding

class HourlyWeatherInfoAdapter: RecyclerView.Adapter<HourlyWeatherInfoAdapter.HourlyWeatherInfoViewHolder>() {

    private var hourlyInfo: HourlyInfo? = null

    inner class HourlyWeatherInfoViewHolder(val binding: WeatherInfoHourlyItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherInfoViewHolder {
        return HourlyWeatherInfoViewHolder(
            WeatherInfoHourlyItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return hourlyInfo!!.hourList.size
    }

    override fun onBindViewHolder(holder: HourlyWeatherInfoViewHolder, position: Int) {
        val currentHour = hourlyInfo!!.hourList[position]
        val currentDegree = hourlyInfo!!.temperatureList[position]
        val currentWindSpeed = hourlyInfo!!.windSpeedList[position]
        val currentHumidity = hourlyInfo!!.humidityList[position]

        holder.binding.apply {
            tvTimeHourlyLayoutItem.text = currentHour
            tvTemperatureHourlyLayoutItem.text = "$currentDegree celsius"
            tvWindSpeedHourlyLayoutItem.text = "$currentWindSpeed km/h"
            tvHumidityHourlyLayoutItem.text = "$currentHumidity%"
        }
    }

    fun setHourlyInfo (hourlyInfo: HourlyInfo) {
        this.hourlyInfo = hourlyInfo
        notifyDataSetChanged()
    }
}