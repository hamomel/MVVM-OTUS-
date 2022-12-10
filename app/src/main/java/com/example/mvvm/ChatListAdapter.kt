package com.example.mvvm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mvvm.data.Chat
import com.example.mvvm.databinding.ItemChatBinding
import com.squareup.picasso.Picasso

class ChatListAdapter : RecyclerView.Adapter<ChatListViewHolder>() {
    private var chats: MutableList<Chat> = mutableListOf()

    fun setChats(chats: List<Chat>) {
        this.chats = chats.toMutableList()
        notifyItemRangeChanged(0, chats.size)
    }

    fun removeItem(position: Int) {
        chats.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItemAt(position: Int): Chat? {
        require(position >= 0)
        if (position >= chats.size) {
            return null
        } else {
            return chats[position]
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChatBinding.inflate(inflater, parent, false)
        return ChatListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.bind(chats[position])
    }

    override fun getItemCount(): Int = chats.size
}

class ChatListViewHolder(private val binding: ItemChatBinding) : ViewHolder(binding.root) {

    fun bind(chat: Chat) {
        binding.nameTv.text = chat.name
        binding.messageTv.text = chat.lastMessage.message
        binding.timeTv.text = DateFormatter.format(chat.lastMessage.date)
        binding.mutedIv.isVisible = chat.isMuted

        if (chat.lastMessage.authorName == chat.name) {
            binding.senderNameTv.isVisible = false
            binding.senderNameTv.text = null
        } else {
            binding.senderNameTv.text = chat.lastMessage.authorName
        }
        Picasso.get().load(chat.avatarUrl).into(binding.avatarIv)
    }
}