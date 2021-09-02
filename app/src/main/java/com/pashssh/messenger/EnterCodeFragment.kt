package com.pashssh.messenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthProvider
import com.pashssh.messenger.databinding.FragmentEnterCodeBinding
import com.pashssh.messenger.utils.replaceActivity

class EnterCodeFragment (val phoneNumber: String, val id: String) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEnterCodeBinding.inflate(inflater)

        binding.testBtnCode.setOnClickListener {
            val code = binding.registrationCode.text.toString()
            val credential = PhoneAuthProvider.getCredential(id, code)
            AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    (activity as RegistrationActivity).replaceActivity(MainActivity())
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
        }

        return binding.root
    }

}