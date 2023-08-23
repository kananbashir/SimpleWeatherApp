package com.example.weatherapiv1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapiv1.R
import com.example.weatherapiv1.adapters.HourlyWeatherInfoAdapter
import com.example.weatherapiv1.databinding.FragmentDetailsBinding
import com.example.weatherapiv1.utils.WeatherUtils.Companion.getFormattedDate

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var hourlyWeatherInfoAdapter: HourlyWeatherInfoAdapter
    private val args: DetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        hourlyWeatherInfoAdapter = HourlyWeatherInfoAdapter()

        binding.rvHourlyWeatherInfoDetailsFrag.apply {
            adapter = hourlyWeatherInfoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        hourlyWeatherInfoAdapter.setHourlyInfo(args.hourlyInfo)

        getFormattedDate(args.hourlyInfo) { _, dayNum, monthName, yearNum ->
            binding.tvCurrentDateDetailsFrag.text = "$dayNum $monthName $yearNum"
        }

        binding.ivReturnBackIconButtonDetailsFrag.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToHomeFragment())
        }


        return binding.root
    }
}