package com.example.weatherapiv1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapiv1.R
import com.example.weatherapiv1.data.db.entities.weather.WeatherData
import com.example.weatherapiv1.databinding.AllSavedCitiesItemLayoutBinding
import com.example.weatherapiv1.ui.CitiesItemOnClickListener

class CitiesAdapter: RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>() {

    inner class CitiesViewHolder (val binding: AllSavedCitiesItemLayoutBinding): RecyclerView.ViewHolder (binding.root)

    private var weatherDataList: List<WeatherData> = listOf()
    private var citiesItemOnClickListener: CitiesItemOnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        return CitiesViewHolder(
            AllSavedCitiesItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return weatherDataList.size
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val currentItem = weatherDataList[position]

        holder.binding.apply {
            tvPlaceNameSavedCitiesItem.text = "${currentItem.city}"

            if (currentItem.isOnline) {
                root.setBackgroundResource(R.drawable.shape_saved_item_bkg_selected)
                tvPlaceNameSavedCitiesItem.setTextColor(root.resources.getColor(R.color.dark_blue, null))
            } else {
                root.setBackgroundResource(R.drawable.shape_saved_item_bkg_non_selected)
                tvPlaceNameSavedCitiesItem.setTextColor(root.resources.getColor(R.color.white, null))
            }

            root.setOnClickListener {
                if (weatherDataList.size > 1) {
                    citiesItemOnClickListener?.onItemClick(currentItem, position)
                }
            }

        }
    }

    fun setListener (listener: CitiesItemOnClickListener) {
        citiesItemOnClickListener = listener
    }

    fun setWeatherDataList (weatherDataList: List<WeatherData>) {
        this.weatherDataList = weatherDataList
        notifyDataSetChanged()
    }

    fun getWeatherDataList (): List<WeatherData> {
        return weatherDataList
    }
}