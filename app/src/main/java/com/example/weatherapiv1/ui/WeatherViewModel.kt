package com.example.weatherapiv1.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapiv1.data.db.entities.geocoding.GeoCodingData
import com.example.weatherapiv1.data.db.entities.weather.Hourly
import com.example.weatherapiv1.data.db.entities.weather.HourlyInfo
import com.example.weatherapiv1.data.db.entities.weather.WeatherData
import com.example.weatherapiv1.data.db.repositories.WeatherRepository
import com.example.weatherapiv1.utils.WeatherUtils.Companion.weatherCodeToDescriptionWithIcon
import com.example.weatherapiv1.utils.WeatherUtils.Companion.getFormattedDate
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class WeatherViewModel (private val weatherRepository: WeatherRepository): ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var _searchIsLoading = MutableLiveData<Boolean>()
    val searchIsLoading: LiveData<Boolean> get() = _searchIsLoading

    private var _geoCodingSearchResults = MutableLiveData<List<GeoCodingData>>()
    val geoCodingSearchResults: LiveData<List<GeoCodingData>> get() = _geoCodingSearchResults

    var allSavedHourlyWeatherData: LiveData<List<WeatherData>> = weatherRepository.getAllSavedHourlyWeatherData()

    fun upsert (weatherData: WeatherData) = viewModelScope.launch {
        weatherRepository.upsert(weatherData)
        delay(2000)
        _isLoading.value = false
    }

    fun delete (weatherData: WeatherData) = viewModelScope.launch {
        weatherRepository.delete(weatherData)
        delay(200)
        allSavedHourlyWeatherData.value?.let { weatherDataList ->
            if (weatherData.isOnline && weatherDataList.isNotEmpty()) {
                setWeatherDataOnline(weatherDataList.last())
            }
        }
    }

    fun getGeoInfoFromApi (placeName: String, view: View) = viewModelScope.launch {
        _searchIsLoading.value = true
        val response = weatherRepository.getGeoInfoFromApi(placeName)
        response.body()?.let {
            _searchIsLoading.value = false
            if (response.body()?.geoCodingData?.get(0)?.name == null) {
                response.message()
                Snackbar.make(view, "No such a place found, please try another location", Snackbar.LENGTH_SHORT).show()
            } else if (response.isSuccessful) {
                if (queryIsCountry(response.body()?.geoCodingData!!)) {
                    Snackbar.make(view, "Please search for a city name. '$placeName' is a country", Snackbar.LENGTH_SHORT).show()
                } else {
                    _geoCodingSearchResults.value = response.body()?.geoCodingData!!
                }
            } else {
                response.message()
            }
        }
    }

    fun getHourlyWeatherInfoFromApi (latitude: Double, longitude: Double, placeName: String) = viewModelScope.launch {
        _isLoading.value = true
        val response = weatherRepository.getHourlyWeatherInfoFromApi(latitude, longitude)
        response.body()?.let {
            if (response.isSuccessful) {
                setWeatherInfo(response.body()?.hourly!!, placeName, latitude, longitude)
            } else {
                response.message()
            }
        }
    }

    private fun setWeatherInfo (hourly: Hourly, placeName: String, latitude: Double, longitude: Double) {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val hourFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val hourlyWeatherInfoList = mutableListOf<HourlyInfo>()

        for (i in 0 until 7) {
            var date = ""
            var hour = ""
            val tempHourList = mutableListOf<String>()
            val tempTemperatureList = mutableListOf<Double>()
            val tempWindSpeedList = mutableListOf<Double>()
            val tempHumidityList = mutableListOf<Int>()
            val tempWeatherCodeList = mutableListOf<String>()
            val tempWeatherCodeIconList = mutableListOf<Pair<Int, Int>>()
            val tempDayNightList = mutableListOf<Int>()

            for (j in i*24 until (i+1)*24) {
                val dateTime = formatter.parse(hourly.time!![j])!!
                date = dateFormat.format(dateTime)
                hour = hourFormat.format(dateTime)

                tempHourList.add(hour)
                tempTemperatureList.add(hourly.temperature_2m!![j])
                tempWindSpeedList.add(hourly.windspeed_10m!![j])
                tempHumidityList.add(hourly.relativehumidity_2m!![j])
                weatherCodeToDescriptionWithIcon(hourly.weathercode!![j]) { weatherCodeString, weatherCodeIconDay, weatherCodeIconNight, _ ->
                    tempWeatherCodeList.add(weatherCodeString)
                    tempWeatherCodeIconList.add(Pair(weatherCodeIconDay, weatherCodeIconNight))
                }
                tempDayNightList.add(hourly.is_day!![j])
            }

            hourlyWeatherInfoList.add(HourlyInfo(date,tempHourList,tempTemperatureList,tempWindSpeedList,tempHumidityList,tempWeatherCodeList, tempWeatherCodeIconList, tempDayNightList))
        }

        getOnlineWeatherData(allSavedHourlyWeatherData.value!!) {onlineWeatherData ->
            onlineWeatherData?.let {
                onlineWeatherData.isOnline = false
                upsert(onlineWeatherData)
            }
        }
        upsert(WeatherData(placeName, latitude, longitude, hourlyWeatherInfoList, true))
    }

    private fun queryIsCountry (geoCodingDataList: List<GeoCodingData>): Boolean {
        for (data in geoCodingDataList) {
            if (data.name == data.country) {
                return true
            }
        }
        return false
    }



    fun clearSearchData () {
        _geoCodingSearchResults.value = listOf()
    }

    fun setWeatherDataOnline (weatherData: WeatherData) {
        weatherData.isOnline = true
        upsert(weatherData)
        setOtherOnlineWeatherDataToOffline(weatherData)
    }

    fun setWeatherDataOnline (placeName: String, view: View) {
        Log.i("KENAN","setWeatherDataOnline: All saved weather data - ${allSavedHourlyWeatherData.value?.size}")
        allSavedHourlyWeatherData.value?.let { weatherDataList ->
            val weatherData = weatherDataList.find { data -> data.city.equals(placeName, true)}
            weatherData?.isOnline = true
            upsert(weatherData!!)
            setOtherOnlineWeatherDataToOffline(weatherData)
            Snackbar.make(view, "Weather information changed to '${weatherData.city}'", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setOtherOnlineWeatherDataToOffline (onlineWeatherData: WeatherData) {
        Log.i("KENAN","setWeatherDataOnline: All saved weather data - ${allSavedHourlyWeatherData.value?.size}")
        var foundOnlineWeatherData: WeatherData? = null

        allSavedHourlyWeatherData.value?.let { weatherDataList ->
            foundOnlineWeatherData = weatherDataList.find { weatherData -> weatherData.isOnline && weatherData.id != onlineWeatherData.id }
            foundOnlineWeatherData?.let {
                foundOnlineWeatherData?.isOnline = false
                upsert(foundOnlineWeatherData!!)
            }
        }
    }

    fun getOnlineWeatherData (weatherDataList: List<WeatherData>, callback: (weatherData: WeatherData?) -> Unit) {
        var onlineWeatherData: WeatherData? = null

        allSavedHourlyWeatherData.value?.let {
            if (weatherDataList.isNotEmpty()) {
                onlineWeatherData = weatherDataList.find { weatherData -> weatherData.isOnline }
            }
        }

        callback(onlineWeatherData)
    }

    fun placeHasAlreadyExist (placeName: String): Boolean {
        var foundWeatherData: WeatherData? = null

        allSavedHourlyWeatherData.value?.let { weatherDataList ->
            foundWeatherData =
                weatherDataList.find { weatherData -> placeName.equals(weatherData.city, true) }
        }

        return foundWeatherData != null
    }

    fun updateOutdatedInfo (weatherData: WeatherData) {
        val calendar = Calendar.getInstance()
        val currentDayNumber = calendar.get(Calendar.DAY_OF_MONTH)

        allSavedHourlyWeatherData.value?.let { weatherDataList ->
            getFormattedDate(weatherData.hourlyInfoList[0]) { _, dayNum, _, _ ->
                if (dayNum.toInt() < currentDayNumber) {
                    getHourlyWeatherInfoFromApi(weatherData.latitude, weatherData.longitude, weatherData.city)
                }
            }
        }
    }
}