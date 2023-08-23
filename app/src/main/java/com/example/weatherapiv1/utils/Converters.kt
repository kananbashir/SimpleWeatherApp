package com.example.weatherapiv1.utils

import androidx.room.TypeConverter
import com.example.weatherapiv1.data.db.entities.weather.HourlyInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    val gson = Gson()

    @TypeConverter
    fun fromHourlyInfoList (hourlyInfoList: List<HourlyInfo>): String {
        return gson.toJson(hourlyInfoList)
    }

    @TypeConverter
    fun toHourlyInfoList (value: String): List<HourlyInfo> {
        val listType = object : TypeToken<List<HourlyInfo>>() {}.type
        return gson.fromJson(value, listType)
    }

}