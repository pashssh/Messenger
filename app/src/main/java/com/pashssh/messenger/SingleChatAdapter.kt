package com.pashssh.messenger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.pashssh.messenger.databinding.FragmentSingleChatBinding
import com.pashssh.messenger.databinding.ItemChatRightBinding

private const val MESSAGE_SENT = 0
private const val MESSAGE_RECEIVED = 1

class SingleChatAdapter(val listMessage: List<MessageEntity>) :
    RecyclerView.Adapter<SingleChatAdapter.SingleChatViewHolder>() {

    inner class SingleChatViewHolder(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatViewHolder {
        return if (viewType == MESSAGE_SENT) {
            SingleChatViewHolder(
                ItemChatRightBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
        } else {
            SingleChatViewHolder(
                ItemChatRightBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
        }
    }

    override fun onBindViewHolder(holder: SingleChatViewHolder, position: Int) {
        if (getItemViewType(position) == MESSAGE_SENT) {
            val item = listMessage[position]
            val binding = (holder as ItemChatRightBinding)
            binding.itemChatTextMessage.text = item.textMessage
        }
    }

    override fun getItemCount(): Int = listMessage.size

    override fun getItemViewType(position: Int): Int {
        return if (listMessage[position].from == CURRENT_UID) {
            MESSAGE_SENT
        } else {
            MESSAGE_RECEIVED
        }
    }

}