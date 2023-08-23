package com.example.weatherapiv1.api.geocoding

object GeoCodingApiProvider {

    private val retrofit = GeoCodingRetrofitInstance().create()

    val geoCodingApi: GeoCodingAPI = retrofit.create(GeoCodingAPI::class.java)

}