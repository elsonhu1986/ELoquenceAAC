package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.R
import com.example.model.AACTile
import com.example.model.SentenceHistoryItem
import com.example.model.SpeechSettings
import com.example.ui.components.IconHelper
import com.example.ui.theme.EloquencePrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SettingsScreen(
  settings: SpeechSettings,
  history: List<SentenceHistoryItem>,
  customTiles: List<AACTile>,
  onUpdateSettings: (SpeechSettings) -> Unit,
  onTestVoice: () -> Unit,
  onSpeakHistoryItem: (String) -> Unit,
  onDeleteHistoryItem: (String) -> Unit,
  onDeleteCustomTile: (String) -> Unit,
  onReplayStartup: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp, vertical = 8.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF0F172A), // Deep dark brand container
        border = BorderStroke(1.dp, Color(0xFFD4AF37).copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Image(
              painter = painterResource(id = R.drawable.brand_eloquence_exact_1791443968399),
              contentDescription = "ELoquence AAC Brand Logo",
              modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "ELoquence",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFFD4AF37) // Metallic gold
                )
              )
              Text(
                text = "COMMUNICATION EMPOWERED",
                style = MaterialTheme.typography.labelSmall.copy(
                  letterSpacing = 1.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFFE2E8F0)
                )
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Developed by ELvolution",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontWeight = FontWeight.SemiBold,
                  color = Color(0xFF94A3B8)
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          OutlinedButton(
            onClick = onReplayStartup,
            colors = ButtonDefaults.outlinedButtonColors(
              contentColor = Color(0xFFD4AF37)
            ),
            border = BorderStroke(1.dp, Color(0xFFD4AF37).copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(
              imageVector = Icons.Default.PlayArrow,
              contentDescription = null,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("Preview Startup Splash Screen")
          }
        }
      }
    }

    item {
      Text(
        text = "Voice & Accessibility Settings",
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      )
      Text(
        text = "Calibrate vocal synthesis, grid density, sensory feedback, and history.",
        style = MaterialTheme.typography.bodyMedium.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      )
    }

    // --- Section 1: Voice Calibration ---
    item {
      SettingsCard(title = "Speech Engine Calibration") {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
          // Speech Rate
          Column {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "Speech Rate",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = String.format(Locale.US, "%.1fx", settings.rate),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
              )
            }
            Slider(
              value = settings.rate,
              onValueChange = { onUpdateSettings(settings.copy(rate = it)) },
              valueRange = 0.5f..2.0f,
              steps = 14,
              modifier = Modifier.testTag("slider_speech_rate")
            )
          }

          // Pitch
          Column {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "Voice Pitch",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = String.format(Locale.US, "%.1fx", settings.pitch),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
              )
            }
            Slider(
              value = settings.pitch,
              onValueChange = { onUpdateSettings(settings.copy(pitch = it)) },
              valueRange = 0.5f..1.5f,
              steps = 9,
              modifier = Modifier.testTag("slider_voice_pitch")
            )
          }

          OutlinedButton(
            onClick = onTestVoice,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("btn_test_voice")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.VolumeUp,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sample Voice Output")
          }
        }
      }
    }

    // --- Section 2: Motor & Sensory Access ---
    item {
      SettingsCard(title = "Motor & Sensory Access") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          SettingSwitchRow(
            label = "Speak Card Immediately on Tap",
            description = "Pronounce each word card aloud as soon as it is selected.",
            checked = settings.speakOnTap,
            onCheckedChange = { onUpdateSettings(settings.copy(speakOnTap = it)) }
          )

          SettingSwitchRow(
            label = "Clear Strip After Speaking",
            description = "Automatically clear the sentence bar once speech finishes.",
            checked = settings.clearAfterSpeak,
            onCheckedChange = { onUpdateSettings(settings.copy(clearAfterSpeak = it)) }
          )

          SettingSwitchRow(
            label = "Haptic Vibration Feedback",
            description = "Provide physical tactile tap feedback on button activation.",
            checked = settings.hapticFeedback,
            onCheckedChange = { onUpdateSettings(settings.copy(hapticFeedback = it)) }
          )

          SettingSwitchRow(
            label = "High-Contrast CVI Mode",
            description = "Dark canvas with vivid neon borders for Cortical Visual Impairment.",
            checked = settings.highContrastCvi,
            onCheckedChange = { onUpdateSettings(settings.copy(highContrastCvi = it)) }
          )
        }
      }
    }

    // --- Section 3: Communication Grid Matrix Density ---
    item {
      SettingsCard(title = "Grid Layout Density") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = "Select number of card columns across the board:",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            listOf(
              Triple(3, "Accessible (3)", "Large"),
              Triple(4, "Standard (4)", "Default"),
              Triple(5, "Dense (5)", "Compact")
            ).forEach { (cols, label, _) ->
              val isSelected = settings.gridColumns == cols
              Surface(
                modifier = Modifier
                  .weight(1f)
                  .clickable { onUpdateSettings(settings.copy(gridColumns = cols)) },
                shape = RoundedCornerShape(10.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh,
                border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null
              ) {
                Column(
                  modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp),
                  horizontalAlignment = Alignment.CenterHorizontally
                ) {
                  Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                  )
                }
              }
            }
          }
        }
      }
    }

    // --- Section 4: Custom Cards List ---
    if (customTiles.isNotEmpty()) {
      item {
        SettingsCard(title = "Custom User Cards (${customTiles.size})") {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            customTiles.forEach { tile ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  if (tile.imageUri != null) {
                    AsyncImage(
                      model = tile.imageUri,
                      contentDescription = null,
                      contentScale = ContentScale.Crop,
                      modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(6.dp))
                    )
                  } else {
                    Icon(
                      imageVector = IconHelper.getIcon(tile.iconName),
                      contentDescription = null,
                      tint = tile.category.lightAccent,
                      modifier = Modifier.size(24.dp)
                    )
                  }
                  Spacer(modifier = Modifier.width(10.dp))
                  Column {
                    Text(
                      text = tile.label,
                      style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                      text = tile.category.displayName,
                      style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                  }
                }

                IconButton(onClick = { onDeleteCustomTile(tile.id) }) {
                  Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete custom card",
                    tint = MaterialTheme.colorScheme.error
                  )
                }
              }
            }
          }
        }
      }
    }

    // --- Section 5: Spoken History ---
    item {
      SettingsCard(title = "Recent Spoken Sentences (${history.size})") {
        if (history.isEmpty()) {
          Text(
            text = "No sentences vocalized yet. Composed sentences will appear here.",
            style = MaterialTheme.typography.bodyMedium.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.padding(vertical = 8.dp)
          )
        } else {
          val dateFormat = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            history.take(10).forEach { item ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = item.sentence,
                    style = MaterialTheme.typography.titleMedium.copy(
                      fontWeight = FontWeight.Bold,
                      fontSize = 15.sp
                    )
                  )
                  Text(
                    text = dateFormat.format(Date(item.timestamp)),
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                  )
                }

                Row {
                  IconButton(onClick = { onSpeakHistoryItem(item.sentence) }) {
                    Icon(
                      imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                      contentDescription = "Re-speak",
                      tint = MaterialTheme.colorScheme.primary
                    )
                  }
                  IconButton(onClick = { onDeleteHistoryItem(item.id) }) {
                    Icon(
                      imageVector = Icons.Default.Close,
                      contentDescription = "Remove from history",
                      tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                  }
                }
              }
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(32.dp))
    }
  }
}

@Composable
private fun SettingsCard(
  title: String,
  content: @Composable () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = MaterialTheme.colorScheme.surfaceContainerLowest,
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.padding(bottom = 12.dp)
      )
      content()
    }
  }
}

@Composable
private fun SettingSwitchRow(
  label: String,
  description: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
      Text(
        text = label,
        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
      )
      Text(
        text = description,
        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
      )
    }
    Switch(
      checked = checked,
      onCheckedChange = onCheckedChange
    )
  }
}
