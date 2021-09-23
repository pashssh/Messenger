package com.pashssh.messenger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.messenger.databinding.FragmentChatsBinding
import com.pashssh.messenger.databinding.ItemChatsBinding
import com.pashssh.messenger.utils.asTime

class ChatsAdapter() : RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>() {

    private var listItemChats = mutableListOf<ItemChats>()
    fun insertItem(item: ItemChats) {
        listItemChats.add(item)
        notifyItemInserted(listItemChats.size)
    }

    class ChatsViewHolder(private val binding: ItemChatsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:ItemChats) {
            binding.itemChatsMessage.text = item.textMessage
            binding.itemChatsMessageTime.text = item.timeMessage.asTime()
            binding.itemChatsName.text = item.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        return ChatsViewHolder(
            ItemChatsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        val item = listItemChats[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listItemChats.size

}