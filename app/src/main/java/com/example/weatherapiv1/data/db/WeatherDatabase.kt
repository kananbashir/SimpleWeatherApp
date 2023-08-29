package com.example.weatherapiv1.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapiv1.data.db.entities.weather.WeatherData
import com.example.weatherapiv1.utils.Converters

@Database (entities = [WeatherData::class], version = 23)
@TypeConverters (Converters::class)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDao

    companion object {

        @Volatile
        private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke (context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase (context).also { instance = it }
        }

        private fun createDatabase (context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java,
                "WeatherInfoDB.db"
            ).fallbackToDestructiveMigration().build()

    }

}