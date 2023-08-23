package com.example.weatherapiv1.data.db.entities.weather

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity ("weather_data_table")
data class WeatherData(
    var city: String,
    val latitude: Double,
    val longitude: Double,
    var hourlyInfoList: List<HourlyInfo>,
    var isOnline: Boolean
) {
    @PrimaryKey (autoGenerate = true)
    var id: Int? = null
}
