package com.example.weatherapiv1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapiv1.data.db.entities.weather.HourlyInfo
import com.example.weatherapiv1.databinding.WeatherInfoDailyItemLayoutBinding
import com.example.weatherapiv1.ui.WeatherItemOnClickListener
import com.example.weatherapiv1.utils.WeatherUtils.Companion.getAverageWeatherInfo
import com.example.weatherapiv1.utils.WeatherUtils.Companion.getFormattedDate
import com.example.weatherapiv1.utils.WeatherUtils.Companion.weatherDescriptionToIcon
import kotlin.math.roundToInt

class DailyWeatherInfoAdapter: RecyclerView.Adapter<DailyWeatherInfoAdapter.DailyWeatherInfoViewHolder>() {

    private var hourlyInfoList: List<HourlyInfo> = listOf()
    private var weatherItemOnClickListener: WeatherItemOnClickListener? = null

    inner class DailyWeatherInfoViewHolder (val binding: WeatherInfoDailyItemLayoutBinding): RecyclerView.ViewHolder (binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherInfoViewHolder {
        return DailyWeatherInfoViewHolder(
            WeatherInfoDailyItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return hourlyInfoList.size
    }

    override fun onBindViewHolder(holder: DailyWeatherInfoViewHolder, position: Int) {
        val currentItem = hourlyInfoList[position]

        holder.binding.apply {
            getFormattedDate(currentItem) { _, dayNumber, monthName, _ ->
                tvDateDailyLayoutItem.text = "$dayNumber $monthName"
            }

            getAverageWeatherInfo(currentItem) { avTemp, avWindSpeed, avHumidity, avWeatherDesc ->
                tvMainDegreeDailyLayoutItem.text = avTemp.roundToInt().toString()
                tvSecondaryDegreeDailyLayoutItem.text = "${avTemp.roundToInt()}°"
                tvHumidityDailyLayoutItem.text = "$avHumidity%"
                tvWindDailyLayoutItem.text = avWindSpeed.roundToInt().toString()
                weatherDescriptionToIcon(avWeatherDesc) { _, _, miniWeatherIcon ->
                    ivWeatherIconDailyLayoutItem.setImageResource(miniWeatherIcon)
                }
            }
            root.setOnClickListener { weatherItemOnClickListener!!.onItemClick(currentItem) }
        }
    }

    fun setWeatherInfo (hourlyInfoList: List<HourlyInfo>) {
        this.hourlyInfoList = hourlyInfoList
        notifyDataSetChanged()
    }

    fun setListener (listener: WeatherItemOnClickListener) {
        weatherItemOnClickListener = listener
    }
}