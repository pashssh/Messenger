package com.pashssh.messenger.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pashssh.messenger.R
import com.pashssh.messenger.initFirebase


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initFirebase()
    }
}