package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.pashssh.messenger.R

class PreferenceFragment2: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref2)
    }
}