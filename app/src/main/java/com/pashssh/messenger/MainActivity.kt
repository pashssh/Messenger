package com.pashssh.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.pashssh.messenger.utils.replaceActivity
import com.pashssh.messenger.utils.replaceFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFirebase()
        if (AUTH.currentUser == null) {
            replaceActivity(RegistrationActivity())
        } else {
            replaceFragment(ChatsFragment(), false)
        }

    }
}