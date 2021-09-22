package com.pashssh.messenger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pashssh.messenger.databinding.FragmentChatsBinding

class ChatsAdapter() : RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>() {

    private var listTextMessage: List<TextMessageEntity> = emptyList()
    fun setList(mListTextMessages: List<TextMessageEntity>) {
        listTextMessage = mListTextMessages
        notifyDataSetChanged()
    }

    class ChatsViewHolder(binding: FragmentChatsBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        return ChatsViewHolder(
            FragmentChatsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = listTextMessage.size

}