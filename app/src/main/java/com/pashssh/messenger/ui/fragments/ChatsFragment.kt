package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.pashssh.messenger.*
import com.pashssh.messenger.databinding.FragmentChatsBinding
import com.pashssh.messenger.utils.*

class ChatsFragment : Fragment(), OnClickList {


    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: ChatsAdapter
    private val mListChats: MutableList<ItemChats> = mutableListOf()

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
        mRecyclerView = binding.chatsRecyclerView
        mAdapter = ChatsAdapter(this)
        mRecyclerView.adapter = mAdapter
        mRefChats = REF_DATABASE.child(MESSAGE_CHILD).child(CURRENT_UID)
        mChatsListener = AppValueEventListener { uids ->
            uids.children.forEach { uid ->
                val lastMessage = uid.children.last().getValue(TextMessageEntity::class.java)
                if (lastMessage != null) {
                    REF_DATABASE.child(USERS_CHILD).child(uid.key.toString())
                        .addListenerForSingleValueEvent(AppValueEventListener {
                            val user = it.getValue(UserEntity::class.java)
                            if (user != null) {
                                val newItem =
                                    ItemChats(
                                        uid.key.toString(),
                                        lastMessage.textMessage,
                                        lastMessage.timeStamp.toString(),
                                        user.username,
                                        user.photoUrl
                                    )
                                mAdapter.insertOrUpdateItem(newItem)
                            }
                        })
                }
            }
        }
        mRefChats.addValueEventListener(mChatsListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(uid: String) {
        requireView().findNavController().navigate(
            ChatsFragmentDirections.actionChatsFragmentToSingleChatFragment(
                uid
            )
        )
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