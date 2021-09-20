package com.pashssh.messenger.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.pashssh.messenger.*
import com.pashssh.messenger.databinding.FragmentEnterCodeBinding
import com.pashssh.messenger.ui.activities.RegistrationActivity
import com.pashssh.messenger.utils.AppValueEventListener
import java.util.concurrent.TimeUnit

class EnterCodeFragment() : Fragment() {

    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mPhoneNumber: String
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEnterCodeBinding.inflate(inflater)
        val args = EnterCodeFragmentArgs.fromBundle(requireArguments())

        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(
                    requireContext(),
                    p0.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                resendToken = token
                storedVerificationId = verificationId

            }
        }

        val phone = args.phoneNumber
        startPhoneNumberVerification(phone)



        binding.testBtnCode.setOnClickListener {
//            val code = binding.registrationCode.text.toString()
//            enterCode(code)
        }

        val regCodeEditText = binding.registrationCode

        regCodeEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val code = regCodeEditText.text.toString()
                Log.d("MYTAG", code)
                if (code.length == 6) {

                    verifyPhoneNumberWithCode(storedVerificationId, code)

                    val view = (activity as RegistrationActivity).currentFocus
                    view?.let { v ->
                        val imm =
                            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(v.windowToken, 0)
                    }
                }
            }
        })

        return binding.root
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(AUTH)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60, TimeUnit.SECONDS)
            .setActivity(this.requireActivity())
            .setCallbacks(mCallback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Log.d("MYTAG", credential.smsCode.toString())
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("MYTAG", "$task task")
                val user = task.result?.user
                if (user != null) {
                    val ref_user = REF_DATABASE.child(PHONES_CHILD)
                        .addListenerForSingleValueEvent(AppValueEventListener { snapshot ->
                            var isContain = false
                            snapshot.children.forEach { snapshot1 ->
                                if (snapshot1.key == user.phoneNumber) {
                                    isContain = true
                                }
                            }
                            if (!isContain) {
                                REF_DATABASE.child(PHONES_CHILD).child(user.phoneNumber!!)
                                    .setValue(user.uid)
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            requireContext(),
                                            it.message.toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    .addOnSuccessListener {
                                        REF_DATABASE.child(USERS_CHILD).child(user.uid)
                                            .setValue(user.toUser())
                                            .addOnFailureListener {
                                                Toast.makeText(
                                                    requireContext(),
                                                    it.message.toString(),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            .addOnSuccessListener {
                                                this.findNavController()
                                                    .navigate(R.id.action_enterCodeFragment_to_mainActivity)
                                            }
                                    }
                            } else {
                                this.findNavController()
                                    .navigate(R.id.action_enterCodeFragment_to_mainActivity)
                            }
                        })


                }
            } else {
                Log.d("MYTAG", task.exception.toString() + " exep")
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(requireContext(), "Неверный код", Toast.LENGTH_SHORT).show()
                }
            }
        }

//        if (it.isSuccessful) {
//            Toast.makeText(requireContext(), "Test", Toast.LENGTH_SHORT)
//                .show()
//            Log.d("MYTAG", it.exception.toString() + " task2")
////                        this@EnterPhoneFragment.findNavController().navigate(R.id.action_enterCodeFragment_to_mainActivity)
//        } else {
//            Toast.makeText(
//                requireContext(),
//                it.exception?.message.toString(),
//                Toast.LENGTH_SHORT
//            ).show()
//        }
    }

//    private fun enterCode(code: String) {
//        val args = EnterCodeFragmentArgs.fromBundle(requireArguments())
//        val credential = PhoneAuthProvider.getCredential(args.id, code)
//        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
//
//        }
//    }

}