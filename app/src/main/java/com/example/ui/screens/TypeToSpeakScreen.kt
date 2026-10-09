package com.example.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EloquencePrimary

@Composable
fun TypeToSpeakScreen(
  isSpeaking: Boolean,
  onSpeakText: (String) -> Unit,
  onAppendToSentence: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var typedText by remember { mutableStateOf("") }
  val quickSnippets = listOf(
    "Yes, thank you.",
    "No, I don't want that.",
    "Please give me a moment.",
    "Can you repeat that?",
    "I need some water.",
    "I am ready now.",
    "That sounds good."
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Column {
      Text(
        text = "Type to Speak",
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      )
      Text(
        text = "Freeform keyboard entry for direct vocalization and custom statements.",
        style = MaterialTheme.typography.bodyMedium.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      )
    }

    // Quick Snippets Row
    val scrollState = rememberScrollState()
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(scrollState),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      quickSnippets.forEach { snippet ->
        SuggestionChip(
          onClick = {
            typedText = if (typedText.isBlank()) snippet else "$typedText $snippet"
          },
          label = {
            Text(
              text = snippet,
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
            )
          },
          shape = RoundedCornerShape(8.dp)
        )
      }
    }

    // Large Input Text Box
    OutlinedTextField(
      value = typedText,
      onValueChange = { typedText = it },
      label = { Text("Enter text to speak aloud") },
      placeholder = { Text("Type here...") },
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .testTag("input_type_to_speak"),
      shape = RoundedCornerShape(16.dp),
      textStyle = MaterialTheme.typography.titleMedium.copy(
        fontSize = 20.sp,
        lineHeight = 28.sp
      ),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = EloquencePrimary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline
      ),
      trailingIcon = {
        if (typedText.isNotEmpty()) {
          IconButton(onClick = { typedText = "" }) {
            Icon(
              imageVector = Icons.Default.Clear,
              contentDescription = "Clear typed text"
            )
          }
        }
      }
    )

    // Action Controls
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      OutlinedButton(
        onClick = {
          if (typedText.isNotBlank()) {
            onAppendToSentence(typedText)
            typedText = ""
          }
        },
        enabled = typedText.isNotBlank(),
        modifier = Modifier
          .weight(1f)
          .height(52.dp)
          .testTag("btn_type_append_strip"),
        shape = RoundedCornerShape(12.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = null,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "To Strip",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
      }

      Button(
        onClick = {
          if (typedText.isNotBlank()) {
            onSpeakText(typedText)
          }
        },
        enabled = typedText.isNotBlank() || isSpeaking,
        modifier = Modifier
          .weight(1.3f)
          .height(52.dp)
          .testTag("btn_type_speak_now"),
        colors = ButtonDefaults.buttonColors(
          containerColor = if (isSpeaking) MaterialTheme.colorScheme.secondary else EloquencePrimary,
          contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.VolumeUp,
          contentDescription = null,
          modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = if (isSpeaking) "Speaking…" else "Speak Now",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
          )
        )
      }
    }
  }
}
