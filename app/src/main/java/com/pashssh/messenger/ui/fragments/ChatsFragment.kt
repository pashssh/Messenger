package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.pashssh.messenger.*
import com.pashssh.messenger.databinding.ChatsFragmentBinding
import com.pashssh.messenger.ui.activities.MainActivity
import com.pashssh.messenger.ui.activities.RegistrationActivity
import com.pashssh.messenger.utils.replaceActivity

class ChatsFragment : Fragment() {



    private lateinit var viewModel: ChatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ChatsFragmentBinding.inflate(inflater)

        binding.logout.setOnClickListener {
            AUTH.signOut()
            (activity as MainActivity).replaceActivity(RegistrationActivity())

            Toast.makeText(requireContext(), "dsfsdfsdf", Toast.LENGTH_SHORT).show()
        }
        binding.write.setOnClickListener {
            REF_DATABASE.child(NODE_USERS).child(AUTH.currentUser!!.uid).setValue(AUTH.currentUser!!.toUser())
        }

        return binding.root
    }


}