package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ServerValue
import com.google.firebase.ktx.Firebase
import com.pashssh.messenger.*
import com.pashssh.messenger.databinding.FragmentSingleChatBinding
import com.pashssh.messenger.ui.activities.MainActivity
import com.pashssh.messenger.utils.AppValueEventListener

class SingleChatFragment : Fragment() {

    var _binding: FragmentSingleChatBinding? = null
    lateinit var toolbarName: TextView
    lateinit var toolbarStatus: TextView
    lateinit var receivingUID: String

    private lateinit var mRefMessage: DatabaseReference
    private lateinit var mMessageListener: AppValueEventListener
    private var mListMessages : List<MessageEntity> = emptyList()
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: SingleChatAdapter


    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        val message = MessageEntity(
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
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).findViewById<View>(R.id.toolbar_chat).visibility = View.VISIBLE
        toolbarName = requireActivity().findViewById(R.id.toolbar_name)
        toolbarName = requireActivity().findViewById(R.id.toolbar_name)
        toolbarStatus = requireActivity().findViewById(R.id.toolbar_status)

        val mRefReceivingUser = REF_DATABASE.child(USERS_CHILD).child(receivingUID)
        val mListenerInfoToolbar = AppValueEventListener {
            val receivingUser = it.getValue(UserEntity::class.java)
            if (receivingUser != null) {
                toolbarName.text = receivingUser.username
                toolbarStatus.text = receivingUser.state.toString()
            }
        }
        mRefReceivingUser.addValueEventListener(mListenerInfoToolbar)
        initRecyclerView()

    }

    private fun initRecyclerView() {
        mRecyclerView = binding.singleChatRecyclerView
        mAdapter = SingleChatAdapter()
        mRefMessage = REF_DATABASE.child(MESSAGE_CHILD).child(CURRENT_UID).child(receivingUID)
        mRecyclerView.adapter = mAdapter

        mMessageListener = AppValueEventListener { snapshot ->
            mListMessages = snapshot.children.map { it.getMessageEntity() }
            Log.d("MYTAG", mListMessages.toString())
            mAdapter.setList(mListMessages)
        }
        mRefMessage.addValueEventListener(mMessageListener)


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

fun DataSnapshot.getMessageEntity(): MessageEntity =
    this.getValue(MessageEntity::class.java) ?: MessageEntity()


