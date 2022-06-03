package com.example.runtracker.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.runtracker.R
import com.example.runtracker.databinding.FragmentStatisticsBinding
import com.example.runtracker.other.CustomMarkerView
import com.example.runtracker.other.TrackingUtility
import com.example.runtracker.ui.viewmodels.MainViewModel
import com.example.runtracker.ui.viewmodels.StatisticsViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.round
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@AndroidEntryPoint
class StatisticsFragment : Fragment (R.layout.fragment_statistics) {

    private val viewModel: StatisticsViewModel by viewModels()

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        setupBarChart()
    }

    private fun setupBarChart() {
        binding.barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
            setDrawGridLines(false)

        }
        binding.barChart.axisLeft.apply {
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
            setDrawGridLines(false)
        }
        binding.barChart.axisRight.apply {
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
            setDrawGridLines(false)
        }
        binding.barChart.apply {
            description.text = "Average Speed Over Time"
            legend.isEnabled = false
        }
    }

    private fun subscribeToObservers() {
        viewModel.totalTime.observe(viewLifecycleOwner, Observer {
            it?.let{
                val totalTime = TrackingUtility.getFormattedStopwatchTime(it)
                binding.tvTotalTime.text = totalTime
            }
        })
        viewModel.totalDistance.observe(viewLifecycleOwner, Observer {
            it?.let{
                val km = it/1000f
                val totalDistance = (km * 10f).roundToInt() / 10f
                val totalDistanceString = "${totalDistance}km"
                binding.tvTotalDistance.text = totalDistanceString
            }
        })
        viewModel.totalAverageSpeed.observe(viewLifecycleOwner, Observer {
            it?.let{
                val averageSpeed = (it * 10f).roundToInt() / 10f
                val averageSpeedString = "${averageSpeed}km/h"
                binding.tvAverageSpeed.text = averageSpeedString
            }
        })
        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalCalories = "${it}kcal"
                binding.tvTotalCalories.text = totalCalories
            }
        })
        viewModel.runsSortedByDate.observe(viewLifecycleOwner, Observer {
            it?.let {
                val allAverageSpeedValues = it.indices.map {i -> BarEntry(i.toFloat(),it[i].averageSpeedInKMH)}
                val barDataSet = BarDataSet(allAverageSpeedValues, "${R.string.average_speed_over_time}")
                    .apply {
                        valueTextColor = Color.BLACK
                        color = ContextCompat.getColor(requireContext(), R.color.red)
                    }
                binding.barChart.data = BarData(barDataSet)
                binding.barChart.marker = CustomMarkerView(it.reversed(),requireContext(),R.layout.marker_view)
                binding.barChart.invalidate()
            }
        })
    }
}