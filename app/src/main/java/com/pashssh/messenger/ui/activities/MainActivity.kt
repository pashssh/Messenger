package com.pashssh.messenger.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pashssh.messenger.AUTH
import com.pashssh.messenger.ui.fragments.ChatsFragment
import com.pashssh.messenger.R
import com.pashssh.messenger.databinding.ActivityMainBinding
import com.pashssh.messenger.initFirebase
import com.pashssh.messenger.utils.replaceActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        initFirebase()
//        if (AUTH.currentUser == null) {
//            replaceActivity(RegistrationActivity())
//        } else {
//            replaceFragment(ChatsFragment(), false)
//        }

    }
}