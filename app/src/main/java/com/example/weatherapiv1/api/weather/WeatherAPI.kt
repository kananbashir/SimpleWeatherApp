package com.example.weatherapiv1.api.weather

import com.example.weatherapiv1.api.weather.response.HourlyWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET ("v1/forecast?hourly=temperature_2m,relativehumidity_2m,weathercode,windspeed_10m,is_day&timezone=auto")
    suspend fun getHourlyWeatherInfoFromApi (
        @Query ("latitude")
        latitude: Double,
        @Query ("longitude")
        longitude: Double
    ): Response<HourlyWeatherResponse>

}