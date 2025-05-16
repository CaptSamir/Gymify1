package com.example.gymify.presentaion.chat

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymify.data.online.ChatGptApi
import com.example.gymify.di.ChatGptRetrofit
import com.example.gymify.domain.models.ChatMessage
import com.example.gymify.domain.models.ChatRequest
import com.example.gymify.domain.models.Message
import com.example.gymify.domain.models.Sender
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @ChatGptRetrofit private val chatGptApi: ChatGptApi
) : ViewModel() {

    private val _messages = mutableStateListOf<ChatMessage>()
    val messages: List<ChatMessage> = _messages

    fun sendMessage(userMessage: String) {
        _messages.add(ChatMessage(userMessage, Sender.USER))

        viewModelScope.launch {
            try {
                val request = ChatRequest(
                    messages = listOf(Message("user", userMessage))
                )
                val response = chatGptApi.sendMessage(request)
                val reply = response.choices.firstOrNull()?.message?.content
                _messages.add(ChatMessage(reply ?: "No response", Sender.ASSISTANT))
                Log.d("ChatDebug", "Sending message: $userMessage")
                Log.d("ChatDebug", "Request: ${Gson().toJson(request)}")
            } catch (e: Exception) {
                _messages.add(ChatMessage("Error: ${e.message}", Sender.ASSISTANT))
            }
        }
    }
}
