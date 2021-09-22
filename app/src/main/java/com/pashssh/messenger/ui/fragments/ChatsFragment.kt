package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.pashssh.messenger.*
import com.pashssh.messenger.databinding.FragmentChatsBinding
import com.pashssh.messenger.utils.AppValueEventListener

class ChatsFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    private lateinit var mRefChats: DatabaseReference
    private lateinit var mRefReceivingUser: DatabaseReference

    private lateinit var mChatsListener: AppValueEventListener
    private var mapListeners = hashMapOf<DatabaseReference, AppValueEventListener>()

    private lateinit var chatsViewModel: ChatsViewModel
    private var _binding: FragmentChatsBinding? = null


    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        chatsViewModel = ViewModelProvider(this).get(ChatsViewModel::class.java)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRefChats = REF_DATABASE.child(MESSAGE_CHILD).child(CURRENT_UID)
        mChatsListener = AppValueEventListener { users ->
            users.children.forEach { user ->
                Log.d("TAGG", " " + user.key)
                Log.d("TAGG", " " + user.children.last())

            }
        }
        mRefChats.addValueEventListener(mChatsListener)
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