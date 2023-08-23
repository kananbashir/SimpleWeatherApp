package com.example.weatherapiv1.ui

import com.example.weatherapiv1.data.db.entities.weather.HourlyInfo

interface WeatherItemOnClickListener {

    fun onItemClick (hourlyInfo: HourlyInfo)

}