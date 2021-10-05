package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.pashssh.messenger.R

class PreferenceFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref)
        findPreference<Preference>("pref2")?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.action_preferenceFragment_to_settingsFragment)
            return@setOnPreferenceClickListener true
        }
    }

}