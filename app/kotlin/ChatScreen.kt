import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.text
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.weight
import kotlin.text.isNotBlank

@androidx.compose.runtime.Composable
fun ChatScreen(viewModel: ChatViewModel = viewModel()) {
    val messages by viewModel.messages.collectAsState()
    var inputText by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }

    androidx.compose.foundation.layout.Column(modifier = androidx.compose.ui.Modifier.fillMaxSize().padding(16.dp)) {
        // 1. Message History
        androidx.compose.foundation.lazy.LazyColumn(
            modifier = androidx.compose.ui.Modifier.weight(1f),
            reverseLayout = false, // Set to true if you want items to start from bottom
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                ChatBubble(message)
            }
        }

        androidx.compose.foundation.layout.Spacer(modifier = androidx.compose.ui.Modifier.height(8.dp))

        // 2. Input Field
        androidx.compose.foundation.layout.Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            androidx.compose.material3.TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = androidx.compose.ui.Modifier.weight(1f),
                placeholder = { androidx.compose.material3.Text("Ask about injury risks...") }
            )
            androidx.compose.material3.IconButton(onClick = {
                if (inputText.isNotBlank()) {
                    viewModel.sendMessage(inputText)
                    inputText = ""
                }
            }) {
                androidx.compose.material3.Icon(Icons.Default.Send, contentDescription = "Send")
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun ChatBubble(message: ChatMessage) {
    val alignment = if (message.isUser) androidx.compose.ui.Alignment.End else androidx.compose.ui.Alignment.Start
    val color =
        if (message.isUser) androidx.compose.material3.MaterialTheme.colorScheme.primary else androidx.compose.material3.MaterialTheme.colorScheme.secondaryContainer

    androidx.compose.foundation.layout.Column(horizontalAlignment = alignment, modifier = androidx.compose.ui.Modifier.fillMaxWidth()) {
        androidx.compose.material3.Surface(
            shape = androidx.compose.material3.MaterialTheme.shapes.medium,
            color = color,
            tonalElevation = 2.dp
        ) {
            androidx.compose.material3.Text(
                text = message.text,
                modifier = androidx.compose.ui.Modifier.padding(12.dp),
                color = if (message.isUser) androidx.compose.ui.graphics.Color.White else androidx.compose.ui.graphics.Color.Unspecified
            )
        }
    }
}