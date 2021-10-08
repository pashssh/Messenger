package com.pashssh.messenger.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceScreen
import com.pashssh.messenger.ImageViewPreference
import com.pashssh.messenger.R
import com.pashssh.messenger.UserEntity
import com.pashssh.messenger.utils.AppValueEventListener
import com.pashssh.messenger.utils.CURRENT_UID
import com.pashssh.messenger.utils.REF_DATABASE
import com.pashssh.messenger.utils.USERS_CHILD
import com.squareup.picasso.Picasso

class MainPreferenceFragment : PreferenceFragmentCompat() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var header: ImageViewPreference
    private lateinit var nickname: Preference


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.main_preferences)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context)
        header = findPreference<ImageViewPreference>("header")!!
        nickname = findPreference<Preference>("nickname")!!

    }

    override fun onResume() {
        super.onResume()
        nickname.title = sharedPreferences.getString("nickname", getString(R.string.nickname))

        header.setImageClickListener(View.OnClickListener {
            Toast.makeText(this.context, "click on image", Toast.LENGTH_SHORT).show()
        })
        REF_DATABASE.child(USERS_CHILD).child(CURRENT_UID).addListenerForSingleValueEvent(
            AppValueEventListener { dataSnapshot ->
                dataSnapshot.getValue(UserEntity::class.java)?.let {
                    header.setText(it.username)
                    header.setImage(it.photoUrl)
                }
            }
        )

    }

}