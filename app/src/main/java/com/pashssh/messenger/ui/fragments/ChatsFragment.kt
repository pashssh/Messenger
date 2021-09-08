package com.pashssh.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.firebase.ktx.Firebase
import com.pashssh.messenger.databinding.ChatsFragmentBinding
import com.pashssh.messenger.utils.replaceActivity

class ChatsFragment : Fragment() {


    private lateinit var viewModel: ChatsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ChatsFragmentBinding.inflate(inflater)

        binding.write.setOnClickListener {
        }

        binding.logout.setOnClickListener {
            AUTH.signOut()

            Toast.makeText(requireContext(), "dsfsdfsdf", Toast.LENGTH_SHORT).show()
        }


//            REF_DATABASE.child(NODE_USERS).child(AUTH.currentUser!!.uid).setValue(AUTH.currentUser!!.toUser())

        return binding.root
    }


}