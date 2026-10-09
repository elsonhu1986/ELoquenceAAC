package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.QuickPhrase
import com.example.ui.components.IconHelper
import com.example.ui.theme.EloquenceError
import com.example.ui.theme.EloquencePrimary

@Composable
fun QuickChatScreen(
  quickPhrases: List<QuickPhrase>,
  favoritePhraseIds: Set<String>,
  isCviMode: Boolean,
  onSpeakPhrase: (String) -> Unit,
  onToggleFavorite: (String) -> Unit,
  onAppendToSentence: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val emergencyPhrases = quickPhrases.filter { it.isHighPriority }
  val generalPhrases = quickPhrases.filter { !it.isHighPriority }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp, vertical = 8.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Text(
        text = "Quick Phrases & Immediate Needs",
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      )
      Text(
        text = "Tap any phrase for instant vocalization without building a sentence card by card.",
        style = MaterialTheme.typography.bodyMedium.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      )
    }

    // Emergency / High Priority Section
    item {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Emergency,
          contentDescription = null,
          tint = EloquenceError,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "High Priority & Urgent Needs",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = if (isCviMode) Color(0xFFFF5252) else EloquenceError
          )
        )
      }
    }

    items(emergencyPhrases) { phrase ->
      QuickPhraseCard(
        phrase = phrase,
        isFavorite = favoritePhraseIds.contains(phrase.id),
        isEmergency = true,
        isCviMode = isCviMode,
        onSpeak = { onSpeakPhrase(phrase.phrase) },
        onToggleFavorite = { onToggleFavorite(phrase.id) },
        onAppend = { onAppendToSentence(phrase.phrase) }
      )
    }

    // Daily Communication Section
    item {
      Text(
        text = "Everyday Phrases & Social Responses",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.padding(top = 12.dp)
      )
    }

    items(generalPhrases) { phrase ->
      QuickPhraseCard(
        phrase = phrase,
        isFavorite = favoritePhraseIds.contains(phrase.id),
        isEmergency = false,
        isCviMode = isCviMode,
        onSpeak = { onSpeakPhrase(phrase.phrase) },
        onToggleFavorite = { onToggleFavorite(phrase.id) },
        onAppend = { onAppendToSentence(phrase.phrase) }
      )
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun QuickPhraseCard(
  phrase: QuickPhrase,
  isFavorite: Boolean,
  isEmergency: Boolean,
  isCviMode: Boolean,
  onSpeak: () -> Unit,
  onToggleFavorite: () -> Unit,
  onAppend: () -> Unit
) {
  val borderColor = when {
    isCviMode && isEmergency -> Color(0xFFFF1744)
    isCviMode -> Color.White
    isEmergency -> Color(0xFFFCA5A5)
    else -> MaterialTheme.colorScheme.outlineVariant
  }

  val containerColor = when {
    isCviMode -> MaterialTheme.colorScheme.surface
    isEmergency -> Color(0xFFFEF2F2)
    else -> MaterialTheme.colorScheme.surfaceContainerLowest
  }

  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onSpeak)
      .testTag("phrase_card_${phrase.id}"),
    shape = RoundedCornerShape(14.dp),
    color = containerColor,
    border = BorderStroke(if (isEmergency || isCviMode) 2.dp else 1.dp, borderColor),
    shadowElevation = if (isEmergency) 2.dp else 1.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = if (isEmergency) Color(0xFFFEE2E2) else MaterialTheme.colorScheme.surfaceContainerHigh,
          modifier = Modifier.size(44.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = IconHelper.getIcon(phrase.iconName),
              contentDescription = null,
              tint = if (isEmergency) Color(0xFFB91C1C) else MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(24.dp)
            )
          }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
          Text(
            text = phrase.title,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = phrase.phrase,
            style = MaterialTheme.typography.bodyMedium.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
          onClick = onAppend,
          modifier = Modifier.size(38.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add to sentence strip",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        IconButton(
          onClick = onToggleFavorite,
          modifier = Modifier.size(38.dp)
        ) {
          Icon(
            imageVector = if (isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
            contentDescription = "Toggle favorite",
            tint = if (isFavorite) Color(0xFFF59E0B) else MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        FilledIconButton(
          onClick = onSpeak,
          colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = if (isEmergency) Color(0xFFB91C1C) else EloquencePrimary
          ),
          modifier = Modifier.size(40.dp)
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = "Speak phrase",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }
  }
}
