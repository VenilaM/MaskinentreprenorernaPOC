package com.example.maskinentreprenrernapoc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow(
        listOf(
            ChatMessage(
                "Hello! I can help you identify workplace injury risks. What's the situation?",
                false
            )
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages

    fun sendMessage(userText: String) {
        // Add user message to list
        val userMsg = ChatMessage(userText, true)
        _messages.value += userMsg

        // Simulate or Call AI
        viewModelScope.launch {
            val response = analyzeRisks(userText)
            _messages.value += response
        }
    }

    private suspend fun analyzeRisks(query: String): ChatMessage {
        delay(1000) // Simulate network
        
        // Example: If user mentions 'excavator', return specific risks
        return if (query.contains("excavator", ignoreCase = true)) {
            ChatMessage(
                text = "I've analyzed the risks for excavator operations in your area:",
                isUser = false,
                risks = listOf(
                    Risk("Underground Utilities", RiskSeverity.HIGH, "High risk of hitting power lines or pipes."),
                    Risk("Ground Stability", RiskSeverity.MEDIUM, "Soft soil may cause machine instability.")
                )
            )
        } else {
            ChatMessage(
                text = "I've identified general risks for your workplace description:",
                isUser = false,
                risks = listOf(
                    Risk("Tripping Hazard", RiskSeverity.LOW, "Ensure walkways are clear of debris.")
                )
            )
        }
    }
}
