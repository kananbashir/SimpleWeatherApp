package com.example.weatherapiv1.api.weather

object WeatherApiProvider {

    private val retrofit = WeatherRetrofitInstance().create()

    val weatherAPI: WeatherAPI = retrofit.create(WeatherAPI::class.java)

}