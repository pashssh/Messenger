package com.pashssh.messenger

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.pashssh.messenger.databinding.FragmentSingleChatBinding
import com.pashssh.messenger.databinding.ItemChatLeftBinding
import com.pashssh.messenger.databinding.ItemChatRightBinding
import com.pashssh.messenger.databinding.ItemContactBinding
import com.pashssh.messenger.utils.asTime
import com.pashssh.messenger.utils.isChecked

private const val MESSAGE_SENT = 0
private const val MESSAGE_RECEIVED = 1

class SingleChatAdapter() :
    RecyclerView.Adapter<SingleChatAdapter.SingleChatViewHolder>() {

    private var listMessage: List<MessageEntity> = emptyList()

    inner class SingleChatViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MessageEntity) {
            if (binding is ItemChatRightBinding) {
                binding.itemChatRightText.text = item.textMessage
                binding.itemChatRightTime.text = item.timeStamp.toString().asTime()
                binding.itemChatRightCheck.setImageResource(item.status.isChecked())
            } else if (binding is ItemChatLeftBinding) {
                binding.itemChatLeftText.text = item.textMessage
                binding.itemChatLeftTime.text = item.timeStamp.toString().asTime()
                binding.itemChatLeftCheck.setImageResource(item.status.isChecked())
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatViewHolder {
        return if (viewType == MESSAGE_SENT) {
            SingleChatViewHolder(
                ItemChatRightBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        } else {
            SingleChatViewHolder(
                ItemChatLeftBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: SingleChatViewHolder, position: Int) {
        val item = listMessage[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listMessage.size


    override fun getItemViewType(position: Int): Int {
        return if (listMessage[position].from == CURRENT_UID) {
            MESSAGE_SENT
        } else {
            MESSAGE_RECEIVED
        }
    }


    fun setList(mListMessages: List<MessageEntity>) {
        listMessage = mListMessages
        notifyDataSetChanged()
    }

}


//package com.pashssh.messenger
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import androidx.viewbinding.ViewBinding
//import com.pashssh.messenger.databinding.FragmentSingleChatBinding
//import com.pashssh.messenger.databinding.ItemChatRightBinding
//
//private const val MESSAGE_SENT = 0
//private const val MESSAGE_RECEIVED = 1
//
//class SingleChatAdapter() :
//    RecyclerView.Adapter<SingleChatAdapter.SingleChatViewHolder>() {
//
//    private var listMessage: List<MessageEntity> = emptyList()
//
//    inner class SingleChatViewHolder(private val binding: ViewBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(messageEntity: MessageEntity) {
//            if (binding is ItemChatRightBinding) {
//                Log.d("MYTAG2", messageEntity.toString())
//                binding.itemChatTextMessage.text = messageEntity.textMessage
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatViewHolder {
//
//        return if (viewType == MESSAGE_SENT) {
//            SingleChatViewHolder(
//                ItemChatRightBinding.inflate(
//                    LayoutInflater.from(parent.context)
//                )
//            )
//        } else {
//            SingleChatViewHolder(
//                ItemChatRightBinding.inflate(
//                    LayoutInflater.from(parent.context)
//                )
//            )
//        }
//    }
//
//    override fun onBindViewHolder(holder: SingleChatViewHolder, position: Int) {
//        val item = listMessage[position]
//        holder.bind(item)
//    }
//
//    override fun getItemCount(): Int = listMessage.size
//
//    override fun getItemViewType(position: Int): Int {
//        return if (listMessage[position].from == CURRENT_UID) {
//            MESSAGE_SENT
//        } else {
//            MESSAGE_RECEIVED
//        }
//    }
//
//    fun setList(mListMessages: List<MessageEntity>) {
//        listMessage = mListMessages
//        notifyDataSetChanged()
//    }
//
//}