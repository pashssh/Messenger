package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pashssh.messenger.R
import com.pashssh.messenger.databinding.FragmentContactsBinding


class ContactsFragment : Fragment() {

    lateinit var binding: FragmentContactsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater)



        return binding.root
    }


}