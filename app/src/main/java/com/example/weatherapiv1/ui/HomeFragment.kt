package com.example.weatherapiv1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapiv1.R
import com.example.weatherapiv1.adapters.DailyWeatherInfoAdapter
import com.example.weatherapiv1.adapters.SearchAdapter
import com.example.weatherapiv1.data.db.WeatherDatabase
import com.example.weatherapiv1.data.db.entities.geocoding.GeoCodingData
import com.example.weatherapiv1.data.db.entities.weather.HourlyInfo
import com.example.weatherapiv1.databinding.FragmentHomeBinding
import com.example.weatherapiv1.utils.WeatherUtils.Companion.getCurrentHourWeatherInfo
import com.example.weatherapiv1.utils.WeatherUtils.Companion.getFormattedDate
import com.example.weatherapiv1.utils.WeatherUtils.Companion.getHourlyInfoOfTheCurrentDay

class HomeFragment : Fragment(), WeatherItemOnClickListener, SearchItemOnClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var weatherDatabase: WeatherDatabase
    private lateinit var dailyWeatherInfoAdapter: DailyWeatherInfoAdapter
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.lySeeAllSavedCitiesHomeFrag.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCitiesFragment())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherDatabase = WeatherDatabase(requireContext())
        weatherViewModel = ViewModelProvider(requireActivity(), WeatherViewModelFactory(weatherDatabase)).get(WeatherViewModel::class.java)

        dailyWeatherInfoAdapter = DailyWeatherInfoAdapter()
        dailyWeatherInfoAdapter.setListener(this)
        searchAdapter = SearchAdapter()
        searchAdapter.setListener(this)

        binding.recyclerView.apply {
            adapter = dailyWeatherInfoAdapter
            layoutManager = LinearLayoutManager (requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.svHomeFrag.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                weatherViewModel.getGeoInfoFromApi(query!!, binding.root)
                binding.svHomeFrag.apply {
                    clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        weatherViewModel.apply {
            geoCodingSearchResults.observe(viewLifecycleOwner) {geoCodingDataList ->
                showDropDown(binding.svHomeFrag, geoCodingDataList)
            }

            isLoading.observe(viewLifecycleOwner) { setLoadingState(it) }
            searchIsLoading.observe(viewLifecycleOwner) { setSearchLoadingState(it) }

            allSavedHourlyWeatherData.observe(viewLifecycleOwner) { weatherDataList ->

                if (weatherDataList.isEmpty()) {
                    binding.tvNoResultHomeFrag.visibility = View.VISIBLE
                    binding.lyCoverHomeFrag.visibility = View.VISIBLE
                    binding.materialCardView.visibility = View.INVISIBLE
                } else {
                    binding.tvNoResultHomeFrag.visibility = View.INVISIBLE
                    binding.lyCoverHomeFrag.visibility = View.INVISIBLE
                    binding.materialCardView.visibility = View.VISIBLE
                }

                getOnlineWeatherData(weatherDataList) { weatherData ->
                    if (weatherData != null) {
                        weatherViewModel.updateOutdatedInfo(weatherData)

                        val currentDayHourlyInfo = getHourlyInfoOfTheCurrentDay(weatherData.hourlyInfoList)

                        getCurrentHourWeatherInfo(currentDayHourlyInfo) { currentTemp, currentWind, currentHum, currentWeatherDesc, currentWeatherCodeIcon ->
                            binding.apply {
                                tvDegreeHomeFrag.text = currentTemp.toInt().toString()
                                tvWeatherInfoHomeFrag.text = currentWeatherDesc
                                tvCurrentWindSpeedHomeFrag.text = "$currentWind km/h"
                                tvCurrentHumidityHomeFrag.text = "$currentHum%"
                                tvCountryNameHomeFrag.text = weatherData.city
                                tvDateHomeFrag.text = getFormattedDate()
                                ivWeatherCodeIconHomeFrag.setImageResource(currentWeatherCodeIcon)
                            }
                        }

                        dailyWeatherInfoAdapter.setWeatherInfo(weatherData.hourlyInfoList)
                        setLoadingState(false)
                    }
                }
            }
        }
    }


    private fun showDropDown (anchorView: SearchView, geoCodingDataList: List<GeoCodingData>) {

            val inflater = LayoutInflater.from(anchorView.context)
            val popupView = inflater.inflate(R.layout.search_pop_up_layout, null)

            val searchResultsRecyclerView = popupView.findViewById<RecyclerView>(R.id.rvSearchResultsPopUpLayout)

            val popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )

            searchResultsRecyclerView.apply {
                adapter = searchAdapter
                layoutManager = LinearLayoutManager (requireContext())
            }

            searchAdapter.differ.submitList(geoCodingDataList)

        if (geoCodingDataList.isNotEmpty()) {
            popupWindow.showAsDropDown(anchorView)
        } else {
            popupWindow.dismiss()
        }
    }

    private fun setLoadingState (loadingState: Boolean) {
        when (loadingState) {
            true -> {
                binding.lyCoverHomeFrag.visibility = View.VISIBLE
                binding.pbLoadingBarHomeFrag.visibility = View.VISIBLE
                binding.materialCardView.visibility = View.INVISIBLE
            }
            false -> {
                binding.lyCoverHomeFrag.visibility = View.INVISIBLE
                binding.pbLoadingBarHomeFrag.visibility = View.INVISIBLE
                binding.materialCardView.visibility = View.VISIBLE
            }
        }
    }

    private fun setSearchLoadingState (loadingState: Boolean) {
        when (loadingState) {
            true -> {
                binding.pbSearchLoadingBarHomeFrag.visibility = View.VISIBLE
            }
            false -> {
                binding.pbSearchLoadingBarHomeFrag.visibility = View.INVISIBLE
            }
        }
    }

    override fun onItemClick(hourlyInfo: HourlyInfo) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(hourlyInfo))
    }

    override fun onItemClick(geoCodingData: GeoCodingData) {
        weatherViewModel.clearSearchData()
        showDropDown(binding.svHomeFrag, emptyList())
        binding.svHomeFrag.apply {
            setQuery("", true)
            clearFocus()
        }

        if (weatherViewModel.placeHasAlreadyExist(geoCodingData.name!!)) {
            weatherViewModel.setWeatherDataOnline(geoCodingData.name, binding.root)
        } else {
            weatherViewModel.getHourlyWeatherInfoFromApi(geoCodingData.latitude!!, geoCodingData.longitude!!, geoCodingData.name)
        }
    }
}