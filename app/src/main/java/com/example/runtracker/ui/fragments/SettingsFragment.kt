package com.example.runtracker.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.runtracker.R
import com.example.runtracker.databinding.FragmentSettingsBinding
import com.example.runtracker.other.Constants.KEY_NAME
import com.example.runtracker.other.Constants.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment (R.layout.fragment_settings) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadValuesFromSharedPreferences()

        binding.btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPreferences()
            if (success) {
                Snackbar.make(view, R.string.changes_saved,Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view, R.string.fill_all_required_fields,Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun loadValuesFromSharedPreferences() {
        val name = sharedPreferences.getString(KEY_NAME,"")
        val weight = sharedPreferences.getFloat(KEY_WEIGHT,65f)
        binding.etName.setText(name)
        binding.etWeight.setText(weight.toString())
    }

    private fun applyChangesToSharedPreferences(): Boolean {
        val nameText = binding.etName.text.toString()
        val weightText = binding.etWeight.text.toString()
        if (nameText.isEmpty() || weightText.isEmpty()) {
            return false
        }
        sharedPreferences.edit()
            .putString(KEY_NAME, nameText)
            .putFloat(KEY_WEIGHT,weightText.toFloat())
            .apply()
        return true
    }
}