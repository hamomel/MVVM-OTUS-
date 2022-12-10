package com.example.mvvm.data

import java.util.Date

class Chat(
    val id: Long,
    val name: String,
    val avatarUrl: String,
    val lastMessage: Message,
    val isMuted: Boolean
)

data class Message(
    val authorName: String,
    val message: String,
    val date: Date
)