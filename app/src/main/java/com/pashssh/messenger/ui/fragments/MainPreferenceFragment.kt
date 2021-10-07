package com.pashssh.messenger.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.pashssh.messenger.R

class MainPreferenceFragment : PreferenceFragmentCompat() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.main_preferences)
        val profileImage = findPreference<Preference>("profile_image")
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context)
    }

    override fun onResume() {
        super.onResume()
        val nickname = findPreference<Preference>("nickname")
        nickname?.title =
            "@" + sharedPreferences.getString("nickname", getString(R.string.nickname))

    }

}