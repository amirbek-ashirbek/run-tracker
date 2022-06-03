package com.example.runtracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.runtracker.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {

    val totalTime = mainRepository.getTotalTimeInMs()
    val totalDistance = mainRepository.getTotalDistanceInM()
    val totalCaloriesBurned = mainRepository.getTotalCaloriesBurned()
    val totalAverageSpeed = mainRepository.getTotalAvgSpeedInKMH()

    val runsSortedByDate = mainRepository.getAllRunsSortedByDate()

}