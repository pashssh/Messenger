package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pashssh.messenger.databinding.FragmentContactsBinding
import com.pashssh.messenger.utils.READ_CONTACTS
import com.pashssh.messenger.utils.checkAndRequestPermission


class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactsBinding.inflate(inflater)

        initContacts()


        return binding.root
    }

    private fun initContacts() {
        checkAndRequestPermission(requireActivity(), READ_CONTACTS)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}