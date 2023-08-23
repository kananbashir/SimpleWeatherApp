package com.example.weatherapiv1.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapiv1.R
import com.example.weatherapiv1.adapters.CitiesAdapter
import com.example.weatherapiv1.data.db.WeatherDatabase
import com.example.weatherapiv1.data.db.entities.weather.WeatherData
import com.example.weatherapiv1.databinding.FragmentCitiesBinding
import com.google.android.material.snackbar.Snackbar
import okhttp3.internal.notify

class CitiesFragment : Fragment(), CitiesItemOnClickListener {
   private lateinit var binding: FragmentCitiesBinding
   private lateinit var weatherViewModel: WeatherViewModel
   private lateinit var weatherDatabase: WeatherDatabase
   private lateinit var citiesAdapter: CitiesAdapter

   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentCitiesBinding.inflate(layoutInflater)

       binding.ivReturnBackIconButtonCitiesFrag.setOnClickListener {
           findNavController().navigate(CitiesFragmentDirections.actionCitiesFragmentToHomeFragment())
       }

       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherDatabase = WeatherDatabase(requireContext())
        weatherViewModel = ViewModelProvider(requireActivity(), WeatherViewModelFactory(weatherDatabase)).get(WeatherViewModel::class.java)
        citiesAdapter = CitiesAdapter()
        citiesAdapter.setListener(this)

        binding.rvCitiesCitiesFrag.apply {
            adapter = citiesAdapter
            layoutManager = LinearLayoutManager (requireContext())
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback (
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val weatherData = citiesAdapter.getWeatherDataList()[position]

                weatherViewModel.delete(weatherData)
                Snackbar.make(binding.root, "Weather information changed to '${weatherData.city}'", Snackbar.LENGTH_SHORT).show()

                Snackbar.make(view, "'${weatherData.city}' deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") { weatherViewModel.upsert(weatherData) }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvCitiesCitiesFrag)
        }

        weatherViewModel.allSavedHourlyWeatherData.observe(viewLifecycleOwner) { weatherDataList ->
            weatherViewModel.getOnlineWeatherData(weatherDataList) { weatherData ->
                if (weatherData != null) {
                    citiesAdapter.setWeatherDataList(weatherDataList)
                }
            }
        }
    }

    override fun onItemClick(weatherData: WeatherData, position: Int) {
        weatherViewModel.setWeatherDataOnline(weatherData)
        citiesAdapter.notifyItemChanged(position)
        Snackbar.make(binding.root, "Weather information changed to '${weatherData.city}'", Snackbar.LENGTH_SHORT).show()
    }
}