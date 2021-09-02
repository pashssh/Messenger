package com.pashssh.messenger

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pashssh.messenger.databinding.ChatsFragmentBinding
import com.pashssh.messenger.utils.replaceActivity

class ChatsFragment : Fragment() {



    private lateinit var viewModel: ChatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ChatsFragmentBinding.inflate(inflater)

        val database = Firebase.database



        binding.logout.setOnClickListener {
            AUTH.signOut()
            (activity as MainActivity).replaceActivity(RegistrationActivity())

            Toast.makeText(requireContext(), "dsfsdfsdf", Toast.LENGTH_SHORT).show()
        }
        binding.write.setOnClickListener {
            REF_DATABASE.child("UUID").setValue(AUTH.uid)
        }


        return binding.root
    }


}