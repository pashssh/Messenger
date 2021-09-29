package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.PhoneAuthProvider
import com.pashssh.messenger.databinding.FragmentEnterPhoneBinding

class EnterPhoneFragment : Fragment() {

    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mPhoneNumber: String

    private var _binding : FragmentEnterPhoneBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEnterPhoneBinding.inflate(inflater)


        binding.testBtn.setOnClickListener {

            val phoneNumber = binding.registrationNumberPhone.text.toString()
            this.findNavController().navigate(EnterPhoneFragmentDirections.actionEnterPhoneFragmentToEnterCodeFragment(phoneNumber))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}