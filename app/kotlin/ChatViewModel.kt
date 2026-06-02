import androidx.activity.result.launch
import androidx.lifecycle.viewModelScope

class ChatViewModel : androidx.lifecycle.ViewModel() {
    private val _messages = kotlinx.coroutines.flow.MutableStateFlow<List<ChatMessage>>(
        kotlin.collections.listOf(
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
        _messages.value = _messages.value + userMsg

        // Simulate or Call AI (e.g., Gemini SDK)
        viewModelScope.launch {
            val aiResponse = getAiResponse(userText)
            _messages.value = _messages.value + ChatMessage(aiResponse, false)
        }
    }

    private suspend fun getAiResponse(query: String): String {
        // Example: If using Gemini, you'd wrap the query with context:
        // "As a safety expert, analyze the following scenario for injury risks: $query"
        kotlinx.coroutines.delay(1000) // Simulate network
        return "Based on your description of using heavy machinery on uneven ground, the primary risks are: 1. Machine tip-over, 2. Ground instability, 3. Pinch points during setup."
    }
}