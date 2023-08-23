package com.example.weatherapiv1.api.geocoding.response

import com.example.weatherapiv1.data.db.entities.geocoding.GeoCodingData
import com.google.gson.annotations.SerializedName

data class GeoCodingResponse(
    @SerializedName ("results")
    val geoCodingData: List<GeoCodingData>?
)