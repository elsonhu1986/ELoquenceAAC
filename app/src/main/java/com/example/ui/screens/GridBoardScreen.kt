package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.data.VocabularyData
import com.example.model.AACTile
import com.example.model.FitzgeraldCategory
import com.example.ui.components.AACCard
import com.example.ui.components.AddTileDialog
import com.example.ui.components.CategoryFilterBar
import com.example.ui.components.SentenceStrip

@Composable
fun GridBoardScreen(
  tokens: List<AACTile>,
  tiles: List<AACTile>,
  isSpeaking: Boolean,
  currentFolderId: String?,
  selectedCategory: FitzgeraldCategory?,
  searchQuery: String,
  isCviMode: Boolean,
  gridColumns: Int,
  onTileClick: (AACTile) -> Unit,
  onSpeak: () -> Unit,
  onClear: () -> Unit,
  onDeleteLast: () -> Unit,
  onRemoveTokenAt: (Int) -> Unit,
  onCopySentence: () -> Unit,
  onShareSentence: () -> Unit,
  onSelectCategory: (FitzgeraldCategory?) -> Unit,
  onExitFolder: () -> Unit,
  onSearchQueryChange: (String) -> Unit,
  onAddCustomTile: (AACTile) -> Unit,
  onDeleteTile: (AACTile) -> Unit,
  modifier: Modifier = Modifier
) {
  var showAddDialog by remember { mutableStateOf(false) }
  var showSearchField by remember { mutableStateOf(false) }
  var tilePendingDelete by remember { mutableStateOf<AACTile?>(null) }

  val folderDisplayName = currentFolderId?.let { VocabularyData.folderNames[it] ?: "Subfolder" }
  val cardHeight = when (gridColumns) {
    3 -> 96
    5 -> 78
    else -> 86
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 12.dp, vertical = 6.dp)
  ) {
    // 1. Pinned Fixed Sentence Strip
    SentenceStrip(
      tokens = tokens,
      isSpeaking = isSpeaking,
      isCviMode = isCviMode,
      onSpeak = onSpeak,
      onClear = onClear,
      onDeleteLast = onDeleteLast,
      onRemoveTokenAt = onRemoveTokenAt,
      onCopySentence = onCopySentence,
      onShareSentence = onShareSentence
    )

    Spacer(modifier = Modifier.height(8.dp))

    // 2. Search / Add Card Toolbar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 2.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      if (showSearchField) {
        OutlinedTextField(
          value = searchQuery,
          onValueChange = onSearchQueryChange,
          placeholder = { Text("Search cards...") },
          singleLine = true,
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("input_search_cards"),
          shape = RoundedCornerShape(10.dp),
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Search,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
          },
          trailingIcon = {
            IconButton(onClick = {
              onSearchQueryChange("")
              showSearchField = false
            }) {
              Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Close search",
                modifier = Modifier.size(18.dp)
              )
            }
          }
        )
      } else {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          IconButton(
            onClick = { showSearchField = true },
            modifier = Modifier.size(38.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Search,
              contentDescription = "Search cards",
              tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          if (searchQuery.isNotBlank()) {
            Text(
              text = "Searching: \"$searchQuery\"",
              style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
            )
          }
        }
      }

      Spacer(modifier = Modifier.width(8.dp))

      FilledTonalButton(
        onClick = { showAddDialog = true },
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        modifier = Modifier
          .height(38.dp)
          .testTag("btn_add_card")
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = null,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "New Card",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )
      }
    }

    Spacer(modifier = Modifier.height(4.dp))

    // 3. Category Filter Bar and Breadcrumbs
    CategoryFilterBar(
      selectedCategory = selectedCategory,
      currentFolderName = folderDisplayName,
      onSelectCategory = onSelectCategory,
      onExitFolder = onExitFolder,
      isCviMode = isCviMode
    )

    Spacer(modifier = Modifier.height(8.dp))

    // 4. Communication Grid
    if (tiles.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        contentAlignment = Alignment.Center
      ) {
        if (selectedCategory == FitzgeraldCategory.CUSTOM) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
          ) {
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = FitzgeraldCategory.CUSTOM.lightBg,
              border = BorderStroke(2.dp, FitzgeraldCategory.CUSTOM.lightBorder),
              modifier = Modifier.size(72.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.Add,
                  contentDescription = null,
                  tint = FitzgeraldCategory.CUSTOM.lightAccent,
                  modifier = Modifier.size(36.dp)
                )
              }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
              text = "No custom cards yet",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Create cards with your own words, categories, or personal photos.",
              style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
              )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
              onClick = { showAddDialog = true },
              colors = ButtonDefaults.buttonColors(
                containerColor = FitzgeraldCategory.CUSTOM.lightAccent,
                contentColor = Color.White
              ),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.testTag("btn_custom_empty_add")
            ) {
              Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text("Click to add new card")
            }
          }
        } else {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "No communication cards found",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Try clearing filters or search to view more vocabulary.",
              style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
              onSearchQueryChange("")
              onSelectCategory(null)
              onExitFolder()
            }) {
              Text("Reset to Core")
            }
          }
        }
      }
    } else {
      LazyVerticalGrid(
        columns = GridCells.Fixed(gridColumns),
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .testTag("grid_aac_board"),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
      ) {
        items(
          items = tiles,
          key = { it.id }
        ) { tile ->
          AACCard(
            tile = tile,
            isCviMode = isCviMode,
            cardHeightDp = cardHeight,
            onClick = { onTileClick(tile) },
            onLongClick = { tilePendingDelete = tile }
          )
        }
      }
    }
  }

  // Dialog for long-click delete card confirmation
  if (tilePendingDelete != null) {
    AlertDialog(
      onDismissRequest = { tilePendingDelete = null },
      title = {
        Text(
          text = "Delete Card?",
          style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
      },
      text = {
        Text(
          text = "Are you sure you want to delete the \"${tilePendingDelete?.label}\" card?",
          style = MaterialTheme.typography.bodyLarge
        )
      },
      confirmButton = {
        Button(
          onClick = {
            tilePendingDelete?.let { onDeleteTile(it) }
            tilePendingDelete = null
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
          ),
          modifier = Modifier.testTag("dialog_btn_confirm_delete")
        ) {
          Text("Yes")
        }
      },
      dismissButton = {
        Button(
          onClick = { tilePendingDelete = null },
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
          ),
          border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.outline),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.testTag("dialog_btn_cancel_delete")
        ) {
          Text("No", fontWeight = FontWeight.Bold)
        }
      }
    )
  }

  // Dialog for adding custom card
  if (showAddDialog) {
    AddTileDialog(
      currentFolderId = currentFolderId,
      onDismiss = { showAddDialog = false },
      onSave = {
        onAddCustomTile(it)
        showAddDialog = false
      }
    )
  }
}
