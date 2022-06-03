package com.example.runtracker.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RunDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: Run)

    @Delete
    suspend fun deleteRun(run: Run)

    @Query("SELECT * FROM run_table ORDER BY timestamp DESC")
    fun getAllRunsSortedByDate() : LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY timeInMs DESC")
    fun getAllRunsSortedByTimeInMs() : LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY caloriesBurnt DESC")
    fun getAllRunsSortedByCaloriesBurned() : LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY averageSpeedInKMH DESC")
    fun getAllRunsSortedByAverageSpeedInKMH() : LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY distanceInM DESC")
    fun getAllRunsSortedByDistanceInM() : LiveData<List<Run>>

    @Query("SELECT SUM(timeInMs) FROM run_table")
    fun getTotalTimeInMs() : LiveData<Long>

    @Query("SELECT SUM(caloriesBurnt) FROM run_table")
    fun getTotalCaloriesBurned() : LiveData<Int>

    @Query("SELECT SUM(distanceInM) FROM run_table")
    fun getTotalDistanceInM() : LiveData<Int>

    @Query("SELECT AVG(averageSpeedInKMH) FROM run_table")
    fun getTotalAvgSpeedInKMH() : LiveData<Float>


}