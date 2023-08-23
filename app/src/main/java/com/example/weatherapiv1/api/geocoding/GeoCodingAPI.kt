package com.example.weatherapiv1.api.geocoding

import com.example.weatherapiv1.api.geocoding.response.GeoCodingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodingAPI {

    @GET ("v1/search")
    suspend fun getGeoInfoFromApi (
        @Query ("name")
        placeName: String,
        @Query ("count")
        count: Int
    ): Response<GeoCodingResponse>

}