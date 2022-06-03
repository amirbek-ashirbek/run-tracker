package com.example.runtracker.ui.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runtracker.database.Run
import com.example.runtracker.other.SortType
import com.example.runtracker.other.TrackingUtility
import com.example.runtracker.repositories.MainRepository
import com.example.runtracker.services.Polyline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class MainViewModel @Inject constructor(
    val mainRepository: MainRepository) : ViewModel() {

    private val runsSortedByDate = mainRepository.getAllRunsSortedByDate()
    private val runsSortedByTimeInMs = mainRepository.getAllRunsSortedByTimeInMs()
    private val runsSortedByDistance = mainRepository.getAllRunsSortedByDistanceInM()
    private val runsSortedByAverageSpeed = mainRepository.getAllRunsSortedByAverageSpeedInKMH()
    private val runsSortedByCaloriesBurnt = mainRepository.getAllRunsSortedByCaloriesBurned()

    val runs = MediatorLiveData<List<Run>>()

    var sortType = SortType.DATE

    init {
        runs.addSource(runsSortedByDate) { result ->
            if (sortType == SortType.DATE) {
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByTimeInMs) { result ->
            if (sortType == SortType.RUNNING_TIME) {
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByDistance) { result ->
            if (sortType == SortType.DISTANCE) {
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByAverageSpeed) { result ->
            if (sortType == SortType.AVERAGE_SPEED) {
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByCaloriesBurnt) { result ->
            if (sortType == SortType.CALORIES_BURNT) {
                result?.let { runs.value = it }
            }
        }

    }

    fun insertRun(pathPoints: List<Polyline>,
    currentTimeinMs: Long,
    met: Float,
    weight: Float,
    bmp: Bitmap?) = viewModelScope.launch {
        var distanceInMeters = 0
        for (polyline in pathPoints) {
            distanceInMeters += TrackingUtility.calculatePolylineLength(polyline).toInt()
        }
        val averageSpeed = round((distanceInMeters / 1000f) / (currentTimeinMs / 1000f / 3600) * 10) /10f
        val dateTimestamp = Calendar.getInstance().timeInMillis
        val caloriesBurned = ((currentTimeinMs / 1000f / 60f) * met * 3.5 * weight / 200).toInt()
        val run = Run(bmp, dateTimestamp, averageSpeed, distanceInMeters,currentTimeinMs,caloriesBurned)
        mainRepository.insertRun(run)
    }



    fun sortRuns(sortType: SortType) = when (sortType) {
        SortType.DATE -> runsSortedByDate.value?.let {runs.value = it}
        SortType.RUNNING_TIME -> runsSortedByTimeInMs.value?.let {runs.value = it}
        SortType.DISTANCE -> runsSortedByDistance.value?.let {runs.value = it}
        SortType.AVERAGE_SPEED -> runsSortedByAverageSpeed.value?.let {runs.value = it}
        SortType.CALORIES_BURNT -> runsSortedByCaloriesBurnt.value?.let {runs.value = it}
    }.also {
        this.sortType = sortType
    }
}