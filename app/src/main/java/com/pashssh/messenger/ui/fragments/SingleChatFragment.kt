package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.ServerValue
import com.pashssh.messenger.*
import com.pashssh.messenger.databinding.FragmentSingleChatBinding
import com.pashssh.messenger.ui.activities.MainActivity

class SingleChatFragment : Fragment() {

    var _binding: FragmentSingleChatBinding? = null
    lateinit var toolbarName: TextView
    lateinit var toolbarStatus: TextView

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleChatBinding.inflate(inflater, container, false)

        binding.singleChatSentButton.setOnClickListener {
            val textMessage = binding.singleChatInputText.text.toString()
            sendMessage(textMessage, "xPQWwZJ3g3ZYUxxCKa3ANt25Ec12") {
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