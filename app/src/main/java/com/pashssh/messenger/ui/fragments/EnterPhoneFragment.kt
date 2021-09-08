package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.pashssh.messenger.AUTH
import com.pashssh.messenger.databinding.FragmentEnterPhoneBinding
import com.pashssh.messenger.ui.activities.MainActivity
import com.pashssh.messenger.ui.activities.RegistrationActivity
import com.pashssh.messenger.utils.replaceActivity
import java.util.concurrent.TimeUnit

class EnterPhoneFragment : Fragment() {

    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mPhoneNumber: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentEnterPhoneBinding.inflate(inflater)

        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(requireContext(), "Добро пожаловать", Toast.LENGTH_SHORT)
                            .show()
                        requireView().findNavController().navigate(R.id.action_enterCodeFragment_to_mainActivity)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            it.exception?.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(
                    requireContext(),
                    p0.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                requireView().findNavController().navigate(EnterPhoneFragmentDirections.actionEnterPhoneFragmentToEnterCodeFragment(mPhoneNumber, id))
            }
        }

        binding.testBtn.setOnClickListener {
            mPhoneNumber = binding.registrationNumberPhone.text.toString()
            val options = PhoneAuthOptions.newBuilder(AUTH)
                .setPhoneNumber(mPhoneNumber)
                .setTimeout(60, TimeUnit.SECONDS)
                .setActivity(this.requireActivity())
                .setCallbacks(mCallback)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }

        return binding.root
    }


}