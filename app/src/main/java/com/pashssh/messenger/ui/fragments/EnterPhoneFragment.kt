package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.pashssh.messenger.AUTH
import com.pashssh.messenger.R
import com.pashssh.messenger.databinding.FragmentEnterPhoneBinding
import java.util.concurrent.TimeUnit

class EnterPhoneFragment : Fragment() {

    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mPhoneNumber: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentEnterPhoneBinding.inflate(inflater)



        binding.testBtn.setOnClickListener {
            val phoneNumber = binding.registrationNumberPhone.text.toString()
            this.findNavController().navigate(EnterPhoneFragmentDirections.actionEnterPhoneFragmentToEnterCodeFragment(phoneNumber))
        }

        return binding.root
    }


}