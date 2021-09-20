package com.pashssh.messenger

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.ServerValue
import com.pashssh.messenger.databinding.FragmentChatsBinding
import com.pashssh.messenger.utils.AppValueEventListener

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
            REF_DATABASE.child(USERS_CHILD).child(CURRENT_UID)
                .addValueEventListener(AppValueEventListener {
                Log.d("MYTAG2", it.getValue(UserEntity::class.java).toString())
                })
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