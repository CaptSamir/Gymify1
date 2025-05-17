package com.example.gymify.presentaion.chat


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gymify.presentaion.chat.components.ChatBubble
import com.example.gymify.ui.theme.BackgroundDark
import com.example.gymify.ui.theme.PrimaryRed
import com.example.gymify.ui.theme.PrimaryText
import com.example.gymify.ui.theme.SecondaryText
import com.example.gymify.ui.theme.SurfaceDark


@Composable
fun ChatBot(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel(),
) {

    val messages = viewModel.messages
    var userInput by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(8.dp)
    ) {
        Text(
            text = "Ask your Ai Coach",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = scrollState,
            reverseLayout = true
        ) {
            items(messages.reversed().toList()) {
                ChatBubble(
                    message = it,
                    bubbleColor = PrimaryRed,
                    textColor = PrimaryText
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val interactionSource = remember { MutableInteractionSource() }
            val isFocused by interactionSource.collectIsFocusedAsState()

            val targetHeight = if (isFocused) 80.dp else 56.dp
            val animatedHeight by animateDpAsState(
                targetValue = targetHeight,
                label = "TextFieldFocusAnimation"
            )

            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .height(animatedHeight)
                    .clip(RoundedCornerShape(16.dp)),
                placeholder = {
                    Text(
                        text = "Ask something...",
                        color = SecondaryText,
                        fontSize = 16.sp
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = SurfaceDark,
                    focusedContainerColor = SurfaceDark,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = PrimaryRed,
                    focusedTextColor = PrimaryText,
                    unfocusedTextColor = PrimaryText,
                    focusedPlaceholderColor = SecondaryText,
                    unfocusedPlaceholderColor = SecondaryText
                ),
                interactionSource = interactionSource,
                shape = RoundedCornerShape(16.dp),
                textStyle = TextStyle(fontSize = 16.sp),
                singleLine = false
            )
            IconButton(onClick = {
                if (userInput.isNotBlank()) {
                    viewModel.sendMessage(userInput)
                    userInput = ""
                }
            }) {
                Icon(
                    Icons.Default.Send,
                    contentDescription = "Send",
                    tint = PrimaryRed
                )
            }
        }
    }
}
