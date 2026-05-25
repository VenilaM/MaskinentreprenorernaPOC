package com.example.maskinentreprenrernapoc

data class Risk(
    val title: String,
    val severity: RiskSeverity,
    val description: String
)

enum class RiskSeverity {
    LOW, MEDIUM, HIGH
}

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val risks: List<Risk> = emptyList(),
    val timestamp: Long = java.lang.System.currentTimeMillis()
)
