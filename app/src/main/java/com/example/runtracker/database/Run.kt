package com.example.runtracker.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "run_table")
data class Run(
    var img: Bitmap? = null,
    var timestamp: Long = 0L,
    var averageSpeedInKMH: Float = 0f,
    var distanceInM: Int = 0,
    var timeInMs: Long = 0L,
    var caloriesBurnt: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}