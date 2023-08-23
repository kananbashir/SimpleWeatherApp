package com.example.weatherapiv1.data.db.repositories

import com.example.weatherapiv1.api.geocoding.GeoCodingApiProvider
import com.example.weatherapiv1.api.weather.WeatherApiProvider
import com.example.weatherapiv1.data.db.WeatherDatabase
import com.example.weatherapiv1.data.db.entities.weather.WeatherData

class WeatherRepository (private val weatherDatabase: WeatherDatabase) {

    suspend fun upsert (weatherData: WeatherData) = weatherDatabase.getWeatherDao().upsert(weatherData)

    suspend fun delete (weatherData: WeatherData) = weatherDatabase.getWeatherDao().delete(weatherData)

    fun getAllSavedHourlyWeatherData () = weatherDatabase.getWeatherDao().getAllSavedHourlyWeatherData()

    suspend fun getHourlyWeatherInfoFromApi (latitude: Double, longitude: Double) =
        WeatherApiProvider.weatherAPI.getHourlyWeatherInfoFromApi(latitude, longitude)

    suspend fun getGeoInfoFromApi (placeName: String) =
        GeoCodingApiProvider.geoCodingApi.getGeoInfoFromApi(placeName, 10)
}