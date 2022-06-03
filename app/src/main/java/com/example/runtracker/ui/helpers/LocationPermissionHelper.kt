package com.example.runtracker.ui.helpers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.runtracker.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.runtracker.other.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocationPermissionHelper {

//    private fun requestLocationPermission() {
//        requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//    }


    fun showLocationDeniedOnceAlert(context: Context, launcher: ActivityResultLauncher<String>) = MaterialAlertDialogBuilder(context, R.style.AlertDialogStyle)
        .setTitle(R.string.are_you_sure)
        .setMessage(R.string.location_permission_denied_alert_message)
        .setNegativeButton(R.string.deny_anyway) { _, _ ->
            Toast.makeText(
                context,
                R.string.location_access_denied,
                Toast.LENGTH_SHORT
            ).show()
        }
        .setPositiveButton(R.string.allow) { _, _ ->
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        .create()
        .show()


    fun showLocationDeniedPermanentlyAlert(context: Context) = MaterialAlertDialogBuilder(context, R.style.AlertDialogStyle)
        .setTitle(R.string.location_access_denied)
        .setMessage(R.string.location_permission_denied_permanently_alert_message)
        .setPositiveButton("Ok") { _, _ ->
        }
        .create()
        .show()

    fun showCancelRunAlert(manager: FragmentManager, listener: () -> Unit) {
        CancelTrackingDialog().apply {
            setYesListener {
                listener()
            }
        }.show(manager, Constants.CANCEL_TRACKING_DIALOG_TAG)
    }
}