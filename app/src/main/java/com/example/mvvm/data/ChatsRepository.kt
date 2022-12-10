package com.example.mvvm.data


class ChatsRepository(
    private val generator: ChatGenerator
) {

    private val chats = mutableListOf<Chat>()

    fun getChatsRange(from: Int, to: Int): List<Chat> {
        require(from >= 0)
        require(to >= from)

        if (to > chats.size) {
            val newChats = generator.generateChats(to - chats.size)
            chats.addAll(newChats)
        }

        return chats.subList(from, to)
    }

    fun removeChat(id: Long) {
        val chat = chats.find { it.id == id }
        chats.remove(chat)
    }
}