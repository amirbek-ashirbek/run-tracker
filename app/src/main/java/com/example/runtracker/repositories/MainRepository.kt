package com.example.runtracker.repositories

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.runtracker.database.Run
import com.example.runtracker.database.RunDao
import javax.inject.Inject

class MainRepository @Inject constructor(val runDao: RunDao) {

    suspend fun insertRun(run: Run) = runDao.insertRun(run)

    suspend fun deleteRun(run: Run) = runDao.deleteRun(run)

    fun getAllRunsSortedByDate() = runDao.getAllRunsSortedByDate()

    fun getAllRunsSortedByTimeInMs() = runDao.getAllRunsSortedByTimeInMs()

    fun getAllRunsSortedByCaloriesBurned() = runDao.getAllRunsSortedByCaloriesBurned()

    fun getAllRunsSortedByAverageSpeedInKMH() = runDao.getAllRunsSortedByAverageSpeedInKMH()

    fun getAllRunsSortedByDistanceInM() = runDao.getAllRunsSortedByDistanceInM()

    fun getTotalTimeInMs() = runDao.getTotalTimeInMs()

    fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()

    fun getTotalDistanceInM() = runDao.getTotalDistanceInM()

    fun getTotalAvgSpeedInKMH() = runDao.getTotalAvgSpeedInKMH()
}