package com.pashssh.messenger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.messenger.databinding.ItemChatsBinding
import com.pashssh.messenger.utils.asTime
import com.squareup.picasso.Picasso

class ChatsAdapter(private val onClickList: OnClickList) : RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>() {

    private var listItemChats = mutableListOf<ItemChats>()

    fun insertOrUpdateItem(item: ItemChats) {
        if (listItemChats.isNotEmpty()) {
            var index = -1
            listItemChats.forEachIndexed { i, itemChats ->
                if (itemChats.uid == item.uid) {
                    index = i
                }
            }
            if (index != -1) {
                listItemChats.removeAt(index)
                listItemChats.add(index, item)
                index = -1
            } else {
                listItemChats.add(item)
            }
        } else {
            listItemChats.add(item)
        }
        listItemChats.sortByDescending { it.timeMessage }
        notifyDataSetChanged()
    }


    class ChatsViewHolder(private val binding: ItemChatsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemChats) {
            binding.itemChatsMessage.text = item.textMessage
            binding.itemChatsMessageTime.text = item.timeMessage.asTime()
            binding.itemChatsName.text = item.username
            Picasso.get()
                .load(item.photoUrl)
                .placeholder(R.drawable.ic_contact_placeholder)
                .into(binding.itemChatsImage)
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
        holder.itemView.setOnClickListener{
            onClickList.onClick(item.uid)
        }
    }

    override fun getItemCount(): Int = listItemChats.size

}

interface OnClickList {
    fun onClick(uid: String)
}