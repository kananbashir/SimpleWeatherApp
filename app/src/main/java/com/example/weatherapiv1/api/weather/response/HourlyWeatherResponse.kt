package com.example.weatherapiv1.api.weather.response

import com.example.weatherapiv1.data.db.entities.weather.Hourly

data class HourlyWeatherResponse(
    val elevation: Double?,
    val generationtime_ms: Double?,
    val hourly: Hourly?,
    val hourly_units: HourlyUnits?,
    val latitude: Double?,
    val longitude: Double?,
    val timezone: String?,
    val timezone_abbreviation: String?,
    val utc_offset_seconds: Int?
)