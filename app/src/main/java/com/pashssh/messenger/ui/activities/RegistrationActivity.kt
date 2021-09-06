package com.pashssh.messenger.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pashssh.messenger.ui.fragments.EnterPhoneFragment
import com.pashssh.messenger.R
import com.pashssh.messenger.utils.replaceFragment

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

    }

    override fun onStart() {
        super.onStart()
        replaceFragment(EnterPhoneFragment(), false)
    }
}