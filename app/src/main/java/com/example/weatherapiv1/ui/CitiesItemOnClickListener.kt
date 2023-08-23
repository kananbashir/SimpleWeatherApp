package com.example.weatherapiv1.ui

import com.example.weatherapiv1.data.db.entities.weather.WeatherData

interface CitiesItemOnClickListener {

    fun onItemClick (weatherData: WeatherData, position: Int)

}