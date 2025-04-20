package com.example.gymify.domain.models

data class ChatMessage(
    val content: String,
    val sender: Sender
)

enum class Sender {
    USER, ASSISTANT
}
