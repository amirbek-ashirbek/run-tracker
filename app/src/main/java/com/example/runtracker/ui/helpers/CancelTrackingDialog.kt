package com.example.runtracker.ui.helpers

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.runtracker.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CancelTrackingDialog : DialogFragment() {

    private var yesListener : (() -> Unit)? = null

    fun setYesListener(listener: () -> Unit) {
        yesListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogStyle)
            .setTitle(R.string.cancel_run_question)
            .setMessage(R.string.cancel_run_message_question)
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton("Yes") {_,_ ->
                yesListener?.let { yes ->
                    yes()
                }
            }
            .setNegativeButton("No",null)
            .create()
//             .show()
    }
}