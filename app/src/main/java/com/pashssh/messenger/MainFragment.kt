package com.pashssh.messenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


//
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onStart() {
        super.onStart()
        initFirebase()
        if (AUTH.currentUser == null) {
            this.findNavController().navigate(R.id.action_mainFragment_to_registrationActivity)
        } else {
            this.findNavController().navigate(R.id.action_mainFragment_to_chatsFragment)
        }
    }

}