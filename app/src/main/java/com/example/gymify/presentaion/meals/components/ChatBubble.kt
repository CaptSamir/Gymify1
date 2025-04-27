package com.example.gymify.presentaion.meals.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gymify.domain.models.ChatMessage
import com.example.gymify.domain.models.Sender
import com.example.gymify.ui.theme.PrimaryText

@Composable
fun ChatBubble(message: ChatMessage,   bubbleColor: Color,
               textColor: Color = PrimaryText) {
    val isUser = message.sender == Sender.USER
    val alignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentAlignment = alignment
    ) {
        Text(
            text = message.content,
            color = textColor,
            modifier = Modifier
                .background(color = bubbleColor, shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
        )
    }
}
