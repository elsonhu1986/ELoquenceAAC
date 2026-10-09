package com.example.ui.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.model.AACTile
import com.example.model.FitzgeraldCategory
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

@Composable
fun AddTileDialog(
  currentFolderId: String?,
  onDismiss: () -> Unit,
  onSave: (AACTile) -> Unit
) {
  val context = LocalContext.current
  var label by remember { mutableStateOf("") }
  var spokenText by remember { mutableStateOf("") }
  var selectedCategory by remember { mutableStateOf(FitzgeraldCategory.CUSTOM) }
  var selectedIcon by remember { mutableStateOf("chat") }
  var attachedImageUri by remember { mutableStateOf<String?>(null) }
  var isError by remember { mutableStateOf(false) }

  // Photo Picker Launcher (Zero-permission Android Photo Picker)
  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia(),
    onResult = { uri: Uri? ->
      if (uri != null) {
        val savedPath = saveImageToInternalStorage(context, uri)
        attachedImageUri = savedPath
      }
    }
  )

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = MaterialTheme.colorScheme.surface,
      tonalElevation = 6.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp)
        .testTag("dialog_add_tile")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        Text(
          text = "Create Custom AAC Card",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
        Text(
          text = "Set word label, category color, and attach your own picture or symbol.",
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
          ),
          modifier = Modifier.padding(bottom = 14.dp)
        )

        // Word Label Input
        OutlinedTextField(
          value = label,
          onValueChange = {
            label = it
            if (spokenText.isBlank()) spokenText = it
            isError = false
          },
          label = { Text("Word or Phrase Label") },
          placeholder = { Text("e.g., Buddy, Favorite Snack, Blanket") },
          isError = isError,
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_tile_label")
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Spoken Text Input (Optional override)
        OutlinedTextField(
          value = spokenText,
          onValueChange = { spokenText = it },
          label = { Text("Spoken Voice Text (Optional)") },
          placeholder = { Text("What TTS speaks (defaults to label)") },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_tile_spoken")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Category Picker
        Text(
          text = "Fitzgerald Key Category:",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )
        val categoryScroll = rememberScrollState()
        val orderedDialogCategories = listOf(
          FitzgeraldCategory.CUSTOM,
          FitzgeraldCategory.NOUN_OBJECT,
          FitzgeraldCategory.ACTION_VERB,
          FitzgeraldCategory.PRONOUN_PEOPLE,
          FitzgeraldCategory.DESCRIPTOR_ADJECTIVE,
          FitzgeraldCategory.SOCIAL_ETIQUETTE,
          FitzgeraldCategory.PREPOSITION_GLUE,
          FitzgeraldCategory.QUESTION_NEGATION
        )
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(categoryScroll)
            .padding(vertical = 6.dp),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          orderedDialogCategories.forEach { cat ->
            val isSelected = cat == selectedCategory
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = cat.lightBg,
              border = BorderStroke(if (isSelected) 3.dp else 1.dp, if (isSelected) cat.lightAccent else cat.lightBorder),
              modifier = Modifier
                .clickable { selectedCategory = cat }
                .padding(2.dp)
            ) {
              Text(
                text = cat.displayName,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = cat.lightAccent
                ),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // --- Custom Picture Attachment Section ---
        Text(
          text = "Card Visual Symbol / Photo:",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )

        if (attachedImageUri != null) {
          // Attached Picture Preview Card
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 6.dp)
          ) {
            Row(
              modifier = Modifier.padding(10.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                  model = attachedImageUri,
                  contentDescription = "Attached photo preview",
                  contentScale = ContentScale.Crop,
                  modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                  Text(
                    text = "Personal Photo Attached",
                    style = MaterialTheme.typography.labelMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colorScheme.primary
                    )
                  )
                  Text(
                    text = "Displayed as this card's picture",
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                  )
                }
              }

              Row {
                IconButton(
                  onClick = {
                    photoPickerLauncher.launch(
                      PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                  }
                ) {
                  Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Change photo",
                    tint = MaterialTheme.colorScheme.primary
                  )
                }

                IconButton(onClick = { attachedImageUri = null }) {
                  Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove photo",
                    tint = MaterialTheme.colorScheme.error
                  )
                }
              }
            }
          }
        } else {
          // Photo Attachment Button
          OutlinedButton(
            onClick = {
              photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
              )
            },
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp)
              .testTag("btn_attach_photo")
          ) {
            Icon(
              imageVector = Icons.Default.AddPhotoAlternate,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Attach Personal Picture from Gallery")
          }

          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Or pick an icon symbol below:",
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )

          // Icon Symbol Picker Grid
          LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            modifier = Modifier
              .fillMaxWidth()
              .height(95.dp)
              .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            items(IconHelper.availableIconNames) { iconName ->
              val isSelected = iconName == selectedIcon && attachedImageUri == null
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh,
                border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
                modifier = Modifier
                  .size(38.dp)
                  .clickable {
                    selectedIcon = iconName
                    attachedImageUri = null
                  }
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(
                    imageVector = IconHelper.getIcon(iconName),
                    contentDescription = iconName,
                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.dp)
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Action Buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(
              containerColor = MaterialTheme.colorScheme.secondaryContainer,
              contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.outline),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.testTag("btn_back_home")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Back",
              style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
              )
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Button(
            onClick = {
              if (label.isBlank()) {
                isError = true
              } else {
                val newTile = AACTile(
                  id = "custom_${UUID.randomUUID()}",
                  label = label.trim(),
                  spokenText = if (spokenText.isBlank()) label.trim() else spokenText.trim(),
                  category = selectedCategory,
                  iconName = selectedIcon,
                  imageUri = attachedImageUri,
                  folderId = currentFolderId,
                  isCustom = true
                )
                onSave(newTile)
              }
            },
            modifier = Modifier.testTag("btn_save_tile")
          ) {
            Text("Save Card")
          }
        }
      }
    }
  }
}

/**
 * Copies picked image Uri into application's internal filesDir to ensure persistent lifetime.
 */
private fun saveImageToInternalStorage(context: Context, uri: Uri): String {
  return try {
    val dir = File(context.filesDir, "custom_tile_images")
    if (!dir.exists()) dir.mkdirs()
    val file = File(dir, "tile_${System.currentTimeMillis()}.jpg")
    context.contentResolver.openInputStream(uri)?.use { input ->
      FileOutputStream(file).use { output ->
        input.copyTo(output)
      }
    }
    file.absolutePath
  } catch (_: Exception) {
    uri.toString()
  }
}
