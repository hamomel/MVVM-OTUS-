package com.example.mvvm

import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.mvvm.data.ChatGenerator
import com.example.mvvm.data.ChatsRepository
import com.example.mvvm.databinding.FragmentChatListBinding

class ChatsFragment : Fragment() {

    private var binding: FragmentChatListBinding? = null
    private val adapter = ChatListAdapter()
    private val repository by lazy { ChatsRepository(ChatGenerator(requireContext().applicationContext)) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            chatsRecycler.adapter = adapter

            val decoration = DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
            val padding =
                requireContext().resources.getDimensionPixelSize(R.dimen.chats_divider_padding)
            val insetDrawable = InsetDrawable(decoration.drawable, padding, 0, 0, 0)
            decoration.setDrawable(insetDrawable)
            chatsRecycler.addItemDecoration(decoration)

            val callback = SwipeToDeleteCallback(requireContext()) { position ->
                val chatToRemove = adapter.getItemAt(position)
                chatToRemove?.let { chat ->
                    repository.removeChat(chat.id)
                }
                adapter.removeItem(position)
            }
            ItemTouchHelper(callback).attachToRecyclerView(chatsRecycler)
        }

        loadChats()
    }

    private fun loadChats() {
        binding?.progressBar?.isVisible = true
        val chats = repository.getChatsRange(0, 50)
        adapter.setChats(chats)
        binding?.progressBar?.isVisible = false

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

