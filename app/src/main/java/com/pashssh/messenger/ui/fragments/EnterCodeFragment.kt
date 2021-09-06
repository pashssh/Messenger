package com.pashssh.messenger.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthProvider
import com.pashssh.messenger.AUTH
import com.pashssh.messenger.NODE_USERS
import com.pashssh.messenger.REF_DATABASE
import com.pashssh.messenger.databinding.FragmentEnterCodeBinding
import com.pashssh.messenger.toUser
import com.pashssh.messenger.ui.activities.MainActivity
import com.pashssh.messenger.ui.activities.RegistrationActivity
import com.pashssh.messenger.utils.replaceActivity

class EnterCodeFragment(val phoneNumber: String, val id: String) : Fragment() {


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
            enterCode(code)
        }

        val regCodeEditText = binding.registrationCode

        regCodeEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val code = regCodeEditText.text.toString()
                if (code.length == 6) {
                    enterCode(code)

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

    private fun enterCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result?.user
                if (user != null) {
                    REF_DATABASE.child(NODE_USERS).child(user.uid).setValue(user.toUser())
                }
                (activity as RegistrationActivity).replaceActivity(MainActivity())
            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(requireContext(), "Неверный код", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}