package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pashssh.messenger.R
import com.pashssh.messenger.databinding.FragmentSingleChatBinding
import com.pashssh.messenger.ui.activities.MainActivity

class SingleChatFragment: Fragment() {

    var _binding: FragmentSingleChatBinding? = null

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleChatBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).mToolbar.setLogo(R.drawable.ic_contact_placeholder)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}