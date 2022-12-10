package com.example.mvvm.data

import android.content.Context
import android.content.res.loader.AssetsProvider
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Date
import kotlin.random.Random
import kotlin.streams.toList

private const val TEN_YEARS = 10 * 365 * 24 * 60 * 60 * 1000L

class ChatGenerator(
    val context: Context
) {

    private val names: List<String> by lazy { readNames() }
    private val messages: List<String> by lazy { readMessages() }
    private val dateRandom get() = Random.nextLong(Date().time - TEN_YEARS, Date().time)
    private var nameCounter = 0
    private var id = 0L

    fun generateChats(count: Int): List<Chat> =
        (0..count).map {
            val name = getNextName()
            val url = "https://avatars.dicebear.com/api/personas/${name.replace(" ", "")}.jpg"

            Chat(
                id = id++,
                name = name,
                avatarUrl = url,
                isMuted = Random.nextBoolean(),
                lastMessage = Message(
                    authorName = if (Random.nextBoolean()) getNextName() else name,
                    message = messages[Random.nextInt(messages.size)],
                    date = Date(dateRandom)
                )
            )
        }

    private fun getNextName(): String {
        val index = nameCounter % names.size
        nameCounter++
        return names[index]
    }

    private fun readMessages(): List<String> {
        val input = context.assets.open("text.txt")
        return BufferedReader(InputStreamReader(input)).use { reader ->
            reader.lines()
                .filter { it.isNotBlank() }
                .map { it.trim() }
                .toList()
        }
    }

    private fun readNames(): List<String> {
        val input = context.assets.open("names.csv")
        return BufferedReader(InputStreamReader(input)).use { reader ->
            reader.lines().skip(1).map { it.replace(";", " ") }.toList()
        }
    }
}