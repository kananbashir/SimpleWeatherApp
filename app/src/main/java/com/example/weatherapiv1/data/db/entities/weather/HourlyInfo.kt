package com.example.weatherapiv1.data.db.entities.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HourlyInfo (
    var date: String,
    var hourList: MutableList<String>,
    var temperatureList: MutableList<Double>,
    var windSpeedList: MutableList<Double>,
    var humidityList: MutableList<Int>,
    var weatherCodeList: MutableList<String>,
    var weatherCodeIcon: MutableList<Pair<Int, Int>>, // first - for day, second - for night
    var isDay: MutableList<Int>
): Parcelable