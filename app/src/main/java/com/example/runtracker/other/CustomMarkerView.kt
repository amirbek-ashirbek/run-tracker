package com.example.runtracker.other

import android.content.Context
import android.view.LayoutInflater
import com.example.runtracker.database.Run
import com.example.runtracker.databinding.MarkerViewBinding
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*

class CustomMarkerView(val runs: List<Run>, context: Context, layoutId: Int)
    : MarkerView(context, layoutId) {

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }

    private lateinit var binding: MarkerViewBinding

    init {
        binding = MarkerViewBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if (e == null) return

        val currentRunId = e.x.toInt()
        val run = runs[currentRunId]

        val calendar = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        binding.tvDate.text = dateFormat.format(calendar.time)

        val averageSpeedInKmh = "${run.averageSpeedInKMH}km/h"
        binding.tvAvgSpeed.text = averageSpeedInKmh

        val distanceInKm = "${run.distanceInM / 1000f}km"
        binding.tvDistance.text = distanceInKm

        binding.tvDuration.text = TrackingUtility.getFormattedStopwatchTime(run.timeInMs)

        val caloriesBurned = "${run.caloriesBurnt}kcal"
        binding.tvCaloriesBurned.text = caloriesBurned
    }
}