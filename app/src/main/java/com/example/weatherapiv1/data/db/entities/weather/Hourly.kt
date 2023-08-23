package com.example.weatherapiv1.data.db.entities.weather

data class Hourly(
    val time: List<String>?,
    val temperature_2m: List<Double>?,
    val windspeed_10m: List<Double>?,
    val relativehumidity_2m: List<Int>?,
    val weathercode: List<Int>?,
    val is_day: List<Int>?
)