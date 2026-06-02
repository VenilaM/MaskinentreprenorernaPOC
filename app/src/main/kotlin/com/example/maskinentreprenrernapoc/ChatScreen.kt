package com.example.maskinentreprenrernapoc

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Main UI for the Chat Screen.
 * Displays a list of messages, quick suggestions, and an input field.
 */
@Composable
fun ChatScreen(viewModel: ChatViewModel = viewModel()) {
    // Observe messages from the ViewModel
    val messages by viewModel.messages.collectAsState()
    // Local state for the text input field
    var inputText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // 1. Message History: A scrollable list of chat messages
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = false,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                ChatBubble(message)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 2. Quick Suggestions: Clickable chips to quickly send predefined queries
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val suggestions = listOf("Excavator risks", "Trench safety", "Personal PPE")
            suggestions.forEach { suggestion ->
                SuggestionChip(
                    onClick = { viewModel.sendMessage(suggestion) },
                    label = { Text(suggestion) },
                    icon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 3. Input Field: Text box and send button for user interaction
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Ask about workplace risks...") }
            )
            IconButton(onClick = {
                if (inputText.isNotBlank()) {
                    viewModel.sendMessage(inputText)
                    inputText = ""
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
            }
        }
    }
}

/**
 * Individual message bubble in the chat history.
 * Changes alignment and color based on whether the sender is the user or the AI.
 */
@Composable
fun ChatBubble(message: ChatMessage) {
    val alignment = if (message.isUser) Alignment.End else Alignment.Start
    val color =
        if (message.isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer

    Column(horizontalAlignment = alignment, modifier = Modifier.fillMaxWidth()) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = color,
            tonalElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // Main message text
                Text(
                    text = message.text,
                    color = if (message.isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
                )

                // If the message contains specific risks, display them as cards
                if (message.risks.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    message.risks.forEach { risk ->
                        RiskCard(risk)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

/**
 * Specialized card for displaying a risk identified in the chat.
 * Uses a colored dot to indicate risk severity (High/Medium/Low).
 */
@Composable
fun RiskCard(risk: Risk) {
    // Determine the indicator color based on severity levels
    val severityColor = when (risk.severity) {
        RiskSeverity.HIGH -> Color(0xFFD32F2F)    // Red
        RiskSeverity.MEDIUM -> Color(0xFFF57C00)  // Orange
        RiskSeverity.LOW -> Color(0xFF388E3C)     // Green
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Severity Indicator (Colored Circle)
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .padding(2.dp)
            ) {
                Surface(
                    color = severityColor, 
                    shape = androidx.compose.foundation.shape.CircleShape, 
                    modifier = Modifier.fillMaxSize()
                ) {}
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Risk Details
            Column {
                Text(text = risk.title, style = MaterialTheme.typography.labelLarge)
                Text(text = risk.description, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
