package com.example.weatherapiv1.api.weather.response

data class HourlyUnits(
    val time: String?,
    val temperature_2m: String?,
    val relativehumidity_2m: String?,
    val windspeed_10m: String?,
    val weathercode: String?,
    val is_day: String?
)