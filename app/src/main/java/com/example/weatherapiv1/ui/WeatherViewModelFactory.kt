package com.example.weatherapiv1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapiv1.data.db.WeatherDatabase
import com.example.weatherapiv1.data.db.repositories.WeatherRepository

class WeatherViewModelFactory(private val weatherDatabase: WeatherDatabase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(WeatherRepository(weatherDatabase)) as T
    }

}