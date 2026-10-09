package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.AACTile
import com.example.ui.theme.EloquencePrimary
import com.example.ui.theme.EloquenceSecondary

@Composable
fun SentenceStrip(
  tokens: List<AACTile>,
  isSpeaking: Boolean,
  isCviMode: Boolean,
  onSpeak: () -> Unit,
  onClear: () -> Unit,
  onDeleteLast: () -> Unit,
  onRemoveTokenAt: (Int) -> Unit,
  onCopySentence: () -> Unit,
  onShareSentence: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  // Auto-scroll to end when new tokens added
  LaunchedEffect(tokens.size) {
    if (tokens.isNotEmpty()) {
      scrollState.animateScrollTo(scrollState.maxValue)
    }
  }

  Surface(
    modifier = modifier
      .fillMaxWidth()
      .testTag("sentence_strip"),
    shape = RoundedCornerShape(16.dp),
    color = if (isCviMode) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceContainerLowest,
    border = BorderStroke(
      width = if (isCviMode) 2.dp else 1.5.dp,
      color = if (isCviMode) Color.White else MaterialTheme.colorScheme.outlineVariant
    ),
    shadowElevation = 3.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
      // Row 1: Words list / Chip stream
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .horizontalScroll(scrollState),
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (tokens.isEmpty()) {
          Text(
            text = "Tap cards below to compose a sentence…",
            style = MaterialTheme.typography.bodyLarge.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
              fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(start = 4.dp)
          )
        } else {
          tokens.forEachIndexed { index, tile ->
            SentenceTokenChip(
              tile = tile,
              isCviMode = isCviMode,
              onRemove = { onRemoveTokenAt(index) }
            )
            Spacer(modifier = Modifier.width(6.dp))
          }
        }
      }

      HorizontalDivider(
        modifier = Modifier.padding(vertical = 8.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
      )

      // Row 2: Action Controls Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        // Left Action Buttons: Delete Last, Clear All
        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(
            onClick = onDeleteLast,
            enabled = tokens.isNotEmpty(),
            modifier = Modifier
              .size(44.dp)
              .testTag("btn_backspace")
          ) {
            Icon(
              imageVector = Icons.Default.Backspace,
              contentDescription = "Delete last card",
              tint = if (tokens.isNotEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
            )
          }

          IconButton(
            onClick = onClear,
            enabled = tokens.isNotEmpty(),
            modifier = Modifier
              .size(44.dp)
              .testTag("btn_clear")
          ) {
            Icon(
              imageVector = Icons.Default.DeleteSweep,
              contentDescription = "Clear sentence",
              tint = if (tokens.isNotEmpty()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
            )
          }

          IconButton(
            onClick = onCopySentence,
            enabled = tokens.isNotEmpty(),
            modifier = Modifier
              .size(44.dp)
              .testTag("btn_copy")
          ) {
            Icon(
              imageVector = Icons.Default.ContentCopy,
              contentDescription = "Copy sentence to clipboard",
              tint = if (tokens.isNotEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
            )
          }

          IconButton(
            onClick = onShareSentence,
            enabled = tokens.isNotEmpty(),
            modifier = Modifier
              .size(44.dp)
              .testTag("btn_share")
          ) {
            Icon(
              imageVector = Icons.Default.Share,
              contentDescription = "Share sentence",
              tint = if (tokens.isNotEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
            )
          }
        }

        // Right Primary Action: Large Vocalize / Speak Button
        Button(
          onClick = onSpeak,
          enabled = tokens.isNotEmpty() || isSpeaking,
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = if (isSpeaking) MaterialTheme.colorScheme.secondary else EloquencePrimary,
            contentColor = Color.White
          ),
          contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
          modifier = Modifier
            .height(44.dp)
            .testTag("btn_speak")
        ) {
          Icon(
            imageVector = if (isSpeaking) Icons.Default.Stop else Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = if (isSpeaking) "Speaking…" else "Speak",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            )
          )
        }
      }
    }
  }
}

@Composable
private fun SentenceTokenChip(
  tile: AACTile,
  isCviMode: Boolean,
  onRemove: () -> Unit
) {
  val bgColor = tile.category.getBackgroundColor(isCviMode)
  val borderColor = tile.category.getBorderColor(isCviMode)
  val textColor = tile.category.getTextColor(isCviMode)
  val icon = IconHelper.getIcon(tile.iconName)

  Surface(
    shape = RoundedCornerShape(8.dp),
    color = bgColor,
    border = BorderStroke(1.5.dp, borderColor),
    modifier = Modifier
      .clickable(onClick = onRemove)
      .height(42.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
      if (tile.imageUri != null) {
        AsyncImage(
          model = tile.imageUri,
          contentDescription = null,
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(20.dp)
            .clip(RoundedCornerShape(4.dp))
        )
      } else {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = textColor,
          modifier = Modifier.size(18.dp)
        )
      }
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = tile.label,
        style = MaterialTheme.typography.labelLarge.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp
        ),
        color = textColor
      )
      Spacer(modifier = Modifier.width(4.dp))
      Icon(
        imageVector = Icons.Default.Close,
        contentDescription = "Remove word",
        tint = textColor.copy(alpha = 0.6f),
        modifier = Modifier.size(14.dp)
      )
    }
  }
}
