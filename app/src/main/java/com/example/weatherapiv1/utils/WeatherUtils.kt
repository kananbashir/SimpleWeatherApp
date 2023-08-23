package com.example.weatherapiv1.utils

import com.example.weatherapiv1.R
import com.example.weatherapiv1.data.db.entities.weather.HourlyInfo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class WeatherUtils {

    companion object {

        fun weatherDescriptionToIcon (weatherDescription: String, callback: (dayIcon: Int, nightIcon: Int, miniIcon: Int) -> Unit) {
            return when (weatherDescription) {
                "Clear sky" -> { callback (R.drawable.ic_weather_code_0, R.drawable.ic_weather_code_0_night, R.drawable.ic_weather_code_mini_0) }
                "Mainly clear" -> { callback (R.drawable.ic_weather_code_1_2, R.drawable.ic_weather_code_1_2_night, R.drawable.ic_weather_code_mini_1_2) }
                "Partly cloudy" -> { callback (R.drawable.ic_weather_code_1_2, R.drawable.ic_weather_code_1_2_night, R.drawable.ic_weather_code_mini_1_2) }
                "Overcast" -> { callback (R.drawable.ic_weather_code_3, R.drawable.ic_weather_code_3_night, R.drawable.ic_weather_code_mini_3) }
                "Foggy" -> { callback (R.drawable.ic_weather_code_45_48, R.drawable.ic_weather_code_45_48_night, R.drawable.ic_weather_code_mini_45_48) }
                "Light drizzle" -> { callback (R.drawable.ic_weather_code_51_53_55, R.drawable.ic_weather_code_51_53_55_night, R.drawable.ic_weather_code_mini_51_53_55) }
                "Moderate drizzle" -> { callback (R.drawable.ic_weather_code_51_53_55, R.drawable.ic_weather_code_51_53_55_night, R.drawable.ic_weather_code_mini_51_53_55) }
                "Dense drizzle" -> { callback (R.drawable.ic_weather_code_51_53_55, R.drawable.ic_weather_code_51_53_55_night, R.drawable.ic_weather_code_mini_51_53_55) }
                "Light freezing drizzle" -> { callback (R.drawable.ic_weather_code_56_57, R.drawable.ic_weather_code_56_57_night, R.drawable.ic_weather_code_mini_56_57) }
                "Dense freezing drizzle" -> { callback (R.drawable.ic_weather_code_56_57, R.drawable.ic_weather_code_56_57_night, R.drawable.ic_weather_code_mini_56_57) }
                "Slight rain" -> { callback (R.drawable.ic_weather_code_61_80, R.drawable.ic_weather_code_61_80_night, R.drawable.ic_weather_code_mini_61_66_80) }
                "Moderate rain" -> { callback (R.drawable.ic_weather_code_63_81, R.drawable.ic_weather_code_63_81_night, R.drawable.ic_weather_code_mini_61_66_80) }
                "Heavy rain" -> { callback (R.drawable.ic_weather_code_65_82, R.drawable.ic_weather_code_65_82_night, R.drawable.ic_weather_code_mini_65_82) }
                "Light freezing rain" -> { callback (R.drawable.ic_weather_code_66, R.drawable.ic_weather_code_66_night, R.drawable.ic_weather_code_mini_61_66_80) }
                "Heavy freezing rain" -> { callback (R.drawable.ic_weather_code_67, R.drawable.ic_weather_code_67_night, R.drawable.ic_weather_code_mini_63_67_81) }
                "Slight snow fall" -> { callback (R.drawable.ic_weather_code_71_85, R.drawable.ic_weather_code_71_85_night, R.drawable.ic_weather_code_mini_71_85) }
                "Moderate snow fall" -> { callback (R.drawable.ic_weather_code_73, R.drawable.ic_weather_code_73_night, R.drawable.ic_weather_code_mini_73) }
                "Heavy snow fall" -> { callback (R.drawable.ic_weather_code_75_86, R.drawable.ic_weather_code_75_86_night, R.drawable.ic_weather_code_mini_75) }
                "Snow grains" -> { callback (R.drawable.ic_weather_code_77, R.drawable.ic_weather_code_77_night, R.drawable.ic_weather_code_mini_77) }
                "Slight rain shower" -> { callback (R.drawable.ic_weather_code_61_80, R.drawable.ic_weather_code_61_80_night, R.drawable.ic_weather_code_mini_61_66_80) }
                "Moderate rain shower" -> { callback (R.drawable.ic_weather_code_63_81, R.drawable.ic_weather_code_63_81_night, R.drawable.ic_weather_code_mini_63_67_81) }
                "Violent rain shower" -> { callback (R.drawable.ic_weather_code_65_82, R.drawable.ic_weather_code_65_82_night, R.drawable.ic_weather_code_mini_65_82) }
                "Slight snow shower" -> { callback (R.drawable.ic_weather_code_71_85, R.drawable.ic_weather_code_71_85_night, R.drawable.ic_weather_code_mini_71_85) }
                "Heavy snow shower" -> { callback (R.drawable.ic_weather_code_75_86, R.drawable.ic_weather_code_75_86_night, R.drawable.ic_weather_code_mini_77) }
                "Thunderstorm" -> { callback (R.drawable.ic_weather_code_95_96_99, R.drawable.ic_weather_code_95_96_99_night, R.drawable.ic_weather_code_mini_95_96_99) }
                "Slight thunderstorm with hail" -> { callback (R.drawable.ic_weather_code_95_96_99, R.drawable.ic_weather_code_95_96_99_night, R.drawable.ic_weather_code_mini_95_96_99) }
                "Moderate thunderstorm with hail" -> { callback (R.drawable.ic_weather_code_95_96_99, R.drawable.ic_weather_code_95_96_99_night, R.drawable.ic_weather_code_mini_95_96_99) }
                else -> { callback (0, 0, 0) }
            }
        }

        fun weatherCodeToDescriptionWithIcon (weatherCode: Int, callback: (name: String, dayIcon: Int, nightIcon: Int, miniIcon: Int) -> Unit) {
            return when (weatherCode) {
                0 -> { callback ("Clear sky", R.drawable.ic_weather_code_0, R.drawable.ic_weather_code_0_night, R.drawable.ic_weather_code_mini_0) }
                1 -> { callback ("Mainly clear", R.drawable.ic_weather_code_1_2, R.drawable.ic_weather_code_1_2_night, R.drawable.ic_weather_code_mini_1_2) }
                2 -> { callback ("Partly cloudy", R.drawable.ic_weather_code_1_2, R.drawable.ic_weather_code_1_2_night, R.drawable.ic_weather_code_mini_1_2) }
                3 -> { callback ("Overcast", R.drawable.ic_weather_code_3, R.drawable.ic_weather_code_3_night, R.drawable.ic_weather_code_mini_3) }
                45 -> { callback ("Foggy", R.drawable.ic_weather_code_45_48, R.drawable.ic_weather_code_45_48_night, R.drawable.ic_weather_code_mini_45_48) }
                48 -> { callback ("Foggy", R.drawable.ic_weather_code_45_48, R.drawable.ic_weather_code_45_48_night, R.drawable.ic_weather_code_mini_45_48) }
                51 -> { callback ("Light drizzle", R.drawable.ic_weather_code_51_53_55, R.drawable.ic_weather_code_51_53_55_night, R.drawable.ic_weather_code_mini_51_53_55) }
                53 -> { callback ("Moderate drizzle", R.drawable.ic_weather_code_51_53_55, R.drawable.ic_weather_code_51_53_55_night, R.drawable.ic_weather_code_mini_51_53_55) }
                55 -> { callback ("Dense drizzle", R.drawable.ic_weather_code_51_53_55, R.drawable.ic_weather_code_51_53_55_night, R.drawable.ic_weather_code_mini_51_53_55) }
                56 -> { callback ("Light freezing drizzle", R.drawable.ic_weather_code_56_57, R.drawable.ic_weather_code_56_57_night, R.drawable.ic_weather_code_mini_56_57) }
                57 -> { callback ("Dense freezing drizzle", R.drawable.ic_weather_code_56_57, R.drawable.ic_weather_code_56_57_night, R.drawable.ic_weather_code_mini_56_57) }
                61 -> { callback ("Slight rain", R.drawable.ic_weather_code_61_80, R.drawable.ic_weather_code_61_80_night, R.drawable.ic_weather_code_mini_61_66_80) }
                63 -> { callback ("Moderate rain", R.drawable.ic_weather_code_63_81, R.drawable.ic_weather_code_63_81_night, R.drawable.ic_weather_code_mini_61_66_80) }
                65 -> { callback ("Heavy rain", R.drawable.ic_weather_code_65_82, R.drawable.ic_weather_code_65_82_night, R.drawable.ic_weather_code_mini_65_82) }
                66 -> { callback ("Light freezing rain", R.drawable.ic_weather_code_66, R.drawable.ic_weather_code_66_night, R.drawable.ic_weather_code_mini_61_66_80) }
                67 -> { callback ("Heavy freezing rain", R.drawable.ic_weather_code_67, R.drawable.ic_weather_code_67_night, R.drawable.ic_weather_code_mini_63_67_81) }
                71 -> { callback ("Slight snow fall", R.drawable.ic_weather_code_71_85, R.drawable.ic_weather_code_71_85_night, R.drawable.ic_weather_code_mini_71_85) }
                73 -> { callback ("Moderate snow fall", R.drawable.ic_weather_code_73, R.drawable.ic_weather_code_73_night, R.drawable.ic_weather_code_mini_73) }
                75 -> { callback ("Heavy snow fall", R.drawable.ic_weather_code_75_86, R.drawable.ic_weather_code_75_86_night, R.drawable.ic_weather_code_mini_75) }
                77 -> { callback ("Snow grains", R.drawable.ic_weather_code_77, R.drawable.ic_weather_code_77_night, R.drawable.ic_weather_code_mini_77) }
                80 -> { callback ("Slight rain shower", R.drawable.ic_weather_code_61_80, R.drawable.ic_weather_code_61_80_night, R.drawable.ic_weather_code_mini_61_66_80) }
                81 -> { callback ("Moderate rain shower", R.drawable.ic_weather_code_63_81, R.drawable.ic_weather_code_63_81_night, R.drawable.ic_weather_code_mini_63_67_81) }
                82 -> { callback ("Violent rain shower", R.drawable.ic_weather_code_65_82, R.drawable.ic_weather_code_65_82_night, R.drawable.ic_weather_code_mini_65_82) }
                85 -> { callback ("Slight snow shower", R.drawable.ic_weather_code_71_85, R.drawable.ic_weather_code_71_85_night, R.drawable.ic_weather_code_mini_71_85) }
                86 -> { callback ("Heavy snow shower", R.drawable.ic_weather_code_75_86, R.drawable.ic_weather_code_75_86_night, R.drawable.ic_weather_code_mini_77) }
                95 -> { callback ("Thunderstorm", R.drawable.ic_weather_code_95_96_99, R.drawable.ic_weather_code_95_96_99_night, R.drawable.ic_weather_code_mini_95_96_99) }
                96 -> { callback ("Slight thunderstorm with hail", R.drawable.ic_weather_code_95_96_99, R.drawable.ic_weather_code_95_96_99_night, R.drawable.ic_weather_code_mini_95_96_99) }
                99 -> { callback ("Moderate thunderstorm with hail", R.drawable.ic_weather_code_95_96_99, R.drawable.ic_weather_code_95_96_99_night, R.drawable.ic_weather_code_mini_95_96_99) }
                else -> { callback ("No info", 0, 0, 0) }
            }
        }

        fun getHourlyInfoOfTheCurrentDay (hourlyInfoList: List<HourlyInfo>): HourlyInfo {
            var currentDate = ""

            getCurrentTime { date, _ ->
                currentDate = date
            }

            val hourlyInfoOfTheDay = hourlyInfoList.find { hourlyInfo ->
                hourlyInfo.date == currentDate
            }!!

            return hourlyInfoOfTheDay
        }

        fun getCurrentTime(callback: (String, String) -> Unit) {
            val currentTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat ("yyyy-MM-dd", Locale.getDefault())
            val hourFormat = SimpleDateFormat("HH:00", Locale.getDefault())

            val date = dateFormat.format(currentTime)
            val hour = hourFormat.format(currentTime)

            callback (date, hour)
        }

        fun getCurrentHourWeatherInfo (hourlyInfo: HourlyInfo, callback: (Double, Double, Int, String, Int) -> Unit) {
            var index = 0

            getCurrentTime { _, hour ->
                index = hourlyInfo.hourList.indexOf(hour)
            }

            val currentTemperature = hourlyInfo.temperatureList[index]
            val currentWindSpeed = hourlyInfo.windSpeedList[index]
            val currentHumidity = hourlyInfo.humidityList[index]
            val currentWeatherDesc = hourlyInfo.weatherCodeList[index]
            var currentWeatherCodeIcon = if (hourlyInfo.isDay[index] == 0) {
                hourlyInfo.weatherCodeIcon[index].second
            } else {
                hourlyInfo.weatherCodeIcon[index].first
            }

            callback(
                currentTemperature,
                currentWindSpeed,
                currentHumidity,
                currentWeatherDesc,
                currentWeatherCodeIcon
            )
        }

        fun getAverageWeatherInfo (hourlyInfo: HourlyInfo,
                                   callback: (averageTemperature: Double, averageWindSpeed: Double, averageHumidity: Int, averageWeatherCode: String) -> Unit) {
            val dataSize = hourlyInfo.hourList.size

            val tempMap = mutableMapOf<String, Int>()
            var iterator: Int

            for (i in 0 until dataSize) {
                if (hourlyInfo.weatherCodeList[i] in tempMap) {
                    iterator = tempMap.get(hourlyInfo.weatherCodeList[i])!!
                    iterator++
                    tempMap.put(hourlyInfo.weatherCodeList[i], iterator)
                } else {
                    iterator = 1
                    tempMap.put(hourlyInfo.weatherCodeList[i], iterator)
                }
            }

            val maxValue = tempMap.values.max()
            val averageWeatherCode = getKeyFromValue (tempMap, maxValue)!!

            var totalTemperature = 0.0
            var totalWindSpeed = 0.0
            var totalHumidity = 0

            for (i in 0 until dataSize) {
                totalTemperature += hourlyInfo.temperatureList[i]
                totalWindSpeed += hourlyInfo.windSpeedList[i]
                totalHumidity += hourlyInfo.humidityList[i]
            }

            val averageTemperature = (totalTemperature/dataSize)
            val averageWindSpeed = totalWindSpeed/dataSize
            val averageHumidity = (totalHumidity/dataSize)

            callback (averageTemperature, averageWindSpeed, averageHumidity, averageWeatherCode)
        }

        private fun getKeyFromValue (map: Map<String, Int>, targetValue: Int): String? {
            for ((key, value) in map) {
                if (value == targetValue) {
                    return key
                }
            }
            return null
        }

        fun getFormattedDate (): String {
            val calendar = Calendar.getInstance()
            val formatter = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
            return formatter.format(calendar.time)
        }

        fun getFormattedDate (hourlyInfo: HourlyInfo, callback: (formatterFullDayName: String, formattedDayNumber: String, formattedFullMonthName: String, formattedFullYearNumber: String) -> Unit) {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormatter.parse(hourlyInfo.date)!!

            val fullDayNameFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
            val formatterFullDayName = fullDayNameFormatter.format(date)

            val dayNumberFormatter = SimpleDateFormat ("d", Locale.getDefault())
            val formattedDayNumber = dayNumberFormatter.format(date)

            val fullMonthNameFormatter = SimpleDateFormat("MMMM", Locale.getDefault())
            val formattedFullMonthName = fullMonthNameFormatter.format(date)

            val fullYearNumberFormatter = SimpleDateFormat("yyyy", Locale.getDefault())
            val formattedFullYearNumber = fullYearNumberFormatter.format(date)

            callback (
                formatterFullDayName,
                formattedDayNumber,
                formattedFullMonthName,
                formattedFullYearNumber
                    )
        }
    }
}