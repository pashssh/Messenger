package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ServerValue
import com.pashssh.messenger.*
import com.pashssh.messenger.databinding.FragmentSingleChatBinding
import com.pashssh.messenger.ui.activities.MainActivity
import com.pashssh.messenger.utils.AppValueEventListener
import com.pashssh.messenger.utils.isOnline

class SingleChatFragment : Fragment() {

    private lateinit var toolbarName: TextView
    private lateinit var toolbarStatus: TextView
    private lateinit var receivingUID: String

    private lateinit var mRefMessage: DatabaseReference
    private lateinit var mRefReceivingUser: DatabaseReference

    private lateinit var mMessageListener: AppValueEventListener
    private lateinit var mListenerInfoToolbar: AppValueEventListener

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mListTextMessages: List<TextMessageEntity>

    private var mapListeners = hashMapOf<DatabaseReference, AppValueEventListener>()

    private var _binding: FragmentSingleChatBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleChatBinding.inflate(inflater, container, false)
        val args = SingleChatFragmentArgs.fromBundle(requireArguments())
        receivingUID = args.targetUID

        binding.singleChatSentButton.setOnClickListener {
            val textMessage = binding.singleChatInputText.text.toString()
            sendMessage(textMessage, receivingUID) {
                binding.singleChatInputText.setText("")
            }
        }

        return binding.root
    }

    private fun sendMessage(textMessage: String, receivingUserId: String, function: () -> Unit) {
        val refDialogUser = "$MESSAGE_CHILD/$CURRENT_UID/$receivingUserId"
        val refDialogReceivingUser = "$MESSAGE_CHILD/$receivingUserId/$CURRENT_UID"
        val messageKey = REF_DATABASE.child(refDialogUser).push().key
        val message = TextMessageEntity(
            CURRENT_UID,
            textMessage,
            ServerValue.TIMESTAMP,
            "text"
        )
        val mapDialog = hashMapOf<String, Any>()
        mapDialog["$refDialogUser/$messageKey"] = message
        mapDialog["$refDialogReceivingUser/$messageKey"] = message
        REF_DATABASE.updateChildren(mapDialog)
            .addOnCompleteListener {
                function()
            }
            .addOnFailureListener {
                Toast.makeText(this.context, it.message.toString(), Toast.LENGTH_SHORT).show()
            }

        val refLastMessage = "$MESSAGE_MAIN/$CURRENT_UID/$receivingUserId"
        val refLastMessageReceiving = "$MESSAGE_MAIN/$receivingUserId/$CURRENT_UID"

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).findViewById<View>(R.id.toolbar_chat).visibility = View.VISIBLE

        initToolbar()
        initRecyclerView()
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).findViewById<View>(R.id.toolbar_chat).visibility = View.GONE
        mapListeners.forEach {
            it.key.removeEventListener(it.value)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initToolbar() {
        toolbarName = requireActivity().findViewById(R.id.toolbar_name)
        toolbarName = requireActivity().findViewById(R.id.toolbar_name)
        toolbarStatus = requireActivity().findViewById(R.id.toolbar_status)

        mRefReceivingUser = REF_DATABASE.child(USERS_CHILD).child(receivingUID)
        mListenerInfoToolbar = AppValueEventListener {
            val receivingUser = it.getValue(UserEntity::class.java)
            if (receivingUser != null) {
                toolbarName.text = receivingUser.username
                toolbarStatus.text = receivingUser.state.isOnline(this)
            }
        }
        mRefReceivingUser.addValueEventListener(mListenerInfoToolbar)
        mapListeners[mRefReceivingUser] = mListenerInfoToolbar
    }

    private fun initRecyclerView() {
        mRecyclerView = binding.singleChatRecyclerView
        mAdapter = SingleChatAdapter()
        mRecyclerView.adapter = mAdapter
        mRefMessage = REF_DATABASE.child(MESSAGE_CHILD).child(CURRENT_UID).child(receivingUID)

        mMessageListener = AppValueEventListener { snapshot ->
            mListTextMessages = snapshot.children.map {
                it.getValue(TextMessageEntity::class.java) ?: TextMessageEntity()
            }
            mAdapter.setList(mListTextMessages)
            mRecyclerView.scrollToPosition(mListTextMessages.count())
        }
        mRefMessage.addValueEventListener(mMessageListener)
        mapListeners[mRefMessage] = mMessageListener
    }
}

