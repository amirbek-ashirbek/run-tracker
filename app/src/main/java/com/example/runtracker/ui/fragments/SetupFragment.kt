package com.example.runtracker.ui.fragments

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.runtracker.R
import com.example.runtracker.databinding.FragmentSetupBinding
import com.example.runtracker.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.example.runtracker.other.Constants.KEY_NAME
import com.example.runtracker.other.Constants.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.prefs.AbstractPreferences
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment (R.layout.fragment_setup) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @set:Inject
    var isFirstAppOpening = true

    private var _binding: FragmentSetupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSetupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isFirstAppOpening) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.setupFragment,true)
                .build()
            findNavController().navigate(R.id.action_setupFragment_to_runFragment,
            savedInstanceState,
            navOptions)
        }

        binding.buttonContinue.setOnClickListener {
            val success = putPersonalDataIntoSharedPreferences()
            if (success) {
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            } else {
                Snackbar.make(requireView(), R.string.fill_all_required_fields, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun putPersonalDataIntoSharedPreferences() : Boolean {
        val name = binding.etName.text.toString()
        val weight = binding.etWeight.text.toString()

        if (name.isEmpty() || weight.isEmpty()) {
            return false
        }
        sharedPreferences.edit()
            .putString(KEY_NAME,name)
            .putFloat(KEY_WEIGHT,weight.toFloat())
            .putBoolean(KEY_FIRST_TIME_TOGGLE,false)
            .apply()
        val toolbarText = "Let's go, $name!"
        return true

    }
}