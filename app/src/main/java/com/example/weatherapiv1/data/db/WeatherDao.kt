package com.example.weatherapiv1.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.weatherapiv1.data.db.entities.weather.Hourly
import com.example.weatherapiv1.data.db.entities.weather.HourlyInfo
import com.example.weatherapiv1.data.db.entities.weather.WeatherData

@Dao
interface WeatherDao {

    @Upsert
    suspend fun upsert (weatherData: WeatherData)

    @Delete
    suspend fun delete (weatherData: WeatherData)

    @Query ("SELECT * FROM weather_data_table")
    fun getAllSavedHourlyWeatherData (): LiveData<List<WeatherData>>

}