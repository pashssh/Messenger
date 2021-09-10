package com.pashssh.messenger

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pashssh.messenger.databinding.FragmentChatsBinding

class ChatsFragment : Fragment() {


    private lateinit var chatsViewModel: ChatsViewModel
    private var _binding: FragmentChatsBinding? = null


    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        chatsViewModel = ViewModelProvider(this).get(ChatsViewModel::class.java)

        binding.write.setOnClickListener {
            REF_DATABASE.child("123").child("k2").setValue("v2")
        }

        binding.logout.setOnClickListener {
            AUTH.signOut()
            requireActivity().recreate()

            Toast.makeText(requireContext(), "dsfsdfsdf", Toast.LENGTH_SHORT).show()
        }


        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.app_menu, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return NavigationUI.onNavDestinationSelected(
//            item,
//            requireView().findNavController()
//        ) || super.onOptionsItemSelected(item)
//    }


}