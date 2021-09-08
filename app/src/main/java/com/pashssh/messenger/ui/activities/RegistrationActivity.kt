package com.pashssh.messenger.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.pashssh.messenger.R

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

//        val navControllerReg = this.findNavController(R.id.nav_host_fragment_reg)
//        NavigationUI.setupActionBarWithNavController(this, navControllerReg)

    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navControllerReg = this.findNavController(R.id.nav_host_fragment_reg)
//        return navControllerReg.navigateUp()
//    }
}