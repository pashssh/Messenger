package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.pashssh.messenger.R
import com.pashssh.messenger.databinding.FragmentSingleChatBinding
import com.pashssh.messenger.ui.activities.MainActivity

class SingleChatFragment: Fragment() {

    var _binding: FragmentSingleChatBinding? = null
    lateinit var toolbarName : TextView
    lateinit var toolbarStatus: TextView

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleChatBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).findViewById<View>(R.id.toolbar_chat).visibility = View.VISIBLE
        toolbarName = requireActivity().findViewById(R.id.toolbar_name)
        toolbarStatus = requireActivity().findViewById(R.id.toolbar_status)
        toolbarName.text = "Ivan Sidorov"
        toolbarStatus.text = "Online"

    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).findViewById<View>(R.id.toolbar_chat).visibility = View.GONE

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}