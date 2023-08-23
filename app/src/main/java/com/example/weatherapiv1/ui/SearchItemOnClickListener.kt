package com.example.weatherapiv1.ui

import com.example.weatherapiv1.data.db.entities.geocoding.GeoCodingData

interface SearchItemOnClickListener {

    fun onItemClick (geoCodingData: GeoCodingData)

}