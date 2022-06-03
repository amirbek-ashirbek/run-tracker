package com.example.runtracker.other

import android.location.Location
import com.example.runtracker.services.Polyline
import java.util.concurrent.TimeUnit

object TrackingUtility {

    fun calculatePolylineLength(polyline: Polyline): Float {
        var distance = 0f
        for (i in 0..polyline.size - 2) {
            val position1 = polyline[i]
            val position2 = polyline[i+1]

            val result = FloatArray(1)
            Location.distanceBetween(
                position1.latitude,
                position1.longitude,
                position2.latitude,
                position2.longitude,
                result
            )
            distance += result[0]
        }
        return distance
    }

    fun getFormattedStopwatchTime(milliSeconds: Long, includeMs: Boolean = false): String {

        var ms = milliSeconds
        val hours = TimeUnit.MILLISECONDS.toHours(ms)
        ms -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(ms)
        ms -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(ms)

        if (!includeMs) {
            return "${if(hours < 10) "0" else ""}$hours:" +
                    "${if(minutes < 10) "0" else ""}$minutes:" +
                    "${if(seconds < 10) "0" else ""}$seconds"
        }
        ms -= TimeUnit.SECONDS.toMillis(seconds)
        ms /= 10

        return "${if(hours < 10) "0" else ""}$hours:" +
                "${if(minutes < 10) "0" else ""}$minutes:" +
                "${if(seconds < 10) "0" else ""}$seconds:" +
                "${if(ms < 10) "0" else ""}$ms"

    }
}