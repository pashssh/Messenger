package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.pashssh.messenger.R
import com.pashssh.messenger.databinding.FragmentSettingsBinding
import com.pashssh.messenger.utils.AUTH

class SettingsFragment: Fragment() {

    private var _binding : FragmentSettingsBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)



        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}