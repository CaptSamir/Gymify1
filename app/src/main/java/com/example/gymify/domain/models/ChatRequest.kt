package com.example.gymify.domain.models

data class ChatRequest(
    val messages: List<Message>,
    val model: String = "gpt-4o-mini"
)

data class Message(
    val role: String, // "user" or "assistant"
    val content: String
)
