package com.example.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.R
import com.example.ui.screens.GridBoardScreen
import com.example.ui.screens.QuickChatScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.TypeToSpeakScreen
import com.example.ui.theme.EloquencePrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AACMainScreen(
  viewModel: AACViewModel = viewModel(),
  onReplayStartup: () -> Unit = {}
) {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  val tokens by viewModel.tokens.collectAsStateWithLifecycle()
  val visibleTiles by viewModel.visibleTiles.collectAsStateWithLifecycle()
  val isSpeaking by viewModel.isSpeaking.collectAsStateWithLifecycle()
  val currentFolderId by viewModel.currentFolderId.collectAsStateWithLifecycle()
  val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
  val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
  val quickPhrases by viewModel.quickPhrases.collectAsStateWithLifecycle()
  val favoritePhraseIds by viewModel.favoritePhraseIds.collectAsStateWithLifecycle()
  val history by viewModel.history.collectAsStateWithLifecycle()
  val customTiles by viewModel.customTiles.collectAsStateWithLifecycle()
  val settings by viewModel.settings.collectAsStateWithLifecycle()
  val activeTab by viewModel.activeTab.collectAsStateWithLifecycle()

  // BackHandler: If inside a subfolder, go back to root; if on other tab, go back to Grid tab
  BackHandler(enabled = currentFolderId != null || activeTab != 0) {
    if (currentFolderId != null) {
      viewModel.exitFolder()
    } else if (activeTab != 0) {
      viewModel.setActiveTab(0)
    }
  }

  // Copy sentence to clipboard helper
  val onCopySentence = {
    val text = tokens.joinToString(" ") { it.spokenText }
    if (text.isNotBlank()) {
      val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
      val clip = ClipData.newPlainText("ELoquence AAC Sentence", text)
      clipboard.setPrimaryClip(clip)
      coroutineScope.launch {
        snackbarHostState.showSnackbar("Copied to clipboard: \"$text\"")
      }
    }
  }

  // Share sentence intent helper
  val onShareSentence = {
    val text = tokens.joinToString(" ") { it.spokenText }
    if (text.isNotBlank()) {
      val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
      }
      val shareIntent = Intent.createChooser(sendIntent, "Share AAC Message")
      context.startActivity(shareIntent)
    }
  }

  BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
    val isWideScreen = maxWidth >= 600.dp

    Scaffold(
      modifier = Modifier.fillMaxSize(),
      snackbarHost = { SnackbarHost(snackbarHostState) },
      topBar = {
        TopAppBar(
          title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Image(
                painter = painterResource(id = R.drawable.brand_eloquence_exact_1791443968399),
                contentDescription = "ELoquence Brand Logo",
                modifier = Modifier
                  .size(36.dp)
                  .clip(RoundedCornerShape(6.dp))
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 20.sp
                )
              )
              if (isSpeaking) {
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                  shape = MaterialTheme.shapes.small,
                  color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Icon(
                      imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                      contentDescription = null,
                      tint = MaterialTheme.colorScheme.onSecondaryContainer,
                      modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = "Speaking",
                      style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                      )
                    )
                  }
                }
              }
            }
          },
          actions = {
            // Quick CVI High Contrast Toggle
            IconButton(
              onClick = {
                viewModel.updateSettings(settings.copy(highContrastCvi = !settings.highContrastCvi))
              },
              modifier = Modifier.testTag("btn_toggle_cvi")
            ) {
              Icon(
                imageVector = if (settings.highContrastCvi) Icons.Default.Contrast else Icons.Default.WbSunny,
                contentDescription = "Toggle CVI High Contrast",
                tint = if (settings.highContrastCvi) Color.Yellow else MaterialTheme.colorScheme.onSurface
              )
            }
          },
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
          )
        )
      },
      bottomBar = {
        if (!isWideScreen) {
          NavigationBar(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            tonalElevation = 4.dp
          ) {
            NavigationBarItem(
              selected = activeTab == 0,
              onClick = { viewModel.setActiveTab(0) },
              icon = { Icon(Icons.Default.GridView, contentDescription = "Board") },
              label = { Text("Grid Board") },
              modifier = Modifier.testTag("nav_tab_grid")
            )
            NavigationBarItem(
              selected = activeTab == 1,
              onClick = { viewModel.setActiveTab(1) },
              icon = { Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = "Quick Chat") },
              label = { Text("Quick Chat") },
              modifier = Modifier.testTag("nav_tab_quick_chat")
            )
            NavigationBarItem(
              selected = activeTab == 2,
              onClick = { viewModel.setActiveTab(2) },
              icon = { Icon(Icons.Default.Keyboard, contentDescription = "Type") },
              label = { Text("Type") },
              modifier = Modifier.testTag("nav_tab_type")
            )
            NavigationBarItem(
              selected = activeTab == 3,
              onClick = { viewModel.setActiveTab(3) },
              icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
              label = { Text("Settings") },
              modifier = Modifier.testTag("nav_tab_settings")
            )
          }
        }
      }
    ) { innerPadding ->
      Row(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        // Tablet Side Navigation Rail
        if (isWideScreen) {
          NavigationRail(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            modifier = Modifier.fillMaxHeight()
          ) {
            Spacer(modifier = Modifier.height(16.dp))
            NavigationRailItem(
              selected = activeTab == 0,
              onClick = { viewModel.setActiveTab(0) },
              icon = { Icon(Icons.Default.GridView, contentDescription = "Board") },
              label = { Text("Grid") },
              modifier = Modifier.testTag("rail_tab_grid")
            )
            NavigationRailItem(
              selected = activeTab == 1,
              onClick = { viewModel.setActiveTab(1) },
              icon = { Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = "Quick Chat") },
              label = { Text("Quick") },
              modifier = Modifier.testTag("rail_tab_quick_chat")
            )
            NavigationRailItem(
              selected = activeTab == 2,
              onClick = { viewModel.setActiveTab(2) },
              icon = { Icon(Icons.Default.Keyboard, contentDescription = "Type") },
              label = { Text("Type") },
              modifier = Modifier.testTag("rail_tab_type")
            )
            NavigationRailItem(
              selected = activeTab == 3,
              onClick = { viewModel.setActiveTab(3) },
              icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
              label = { Text("Settings") },
              modifier = Modifier.testTag("rail_tab_settings")
            )
          }
        }

        Box(
          modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
        ) {
          when (activeTab) {
            0 -> GridBoardScreen(
              tokens = tokens,
              tiles = visibleTiles,
              isSpeaking = isSpeaking,
              currentFolderId = currentFolderId,
              selectedCategory = selectedCategory,
              searchQuery = searchQuery,
              isCviMode = settings.highContrastCvi,
              gridColumns = if (isWideScreen && settings.gridColumns < 5) settings.gridColumns + 1 else settings.gridColumns,
              cardSize = settings.cardSize,
              onTileClick = { viewModel.onTileClicked(it) },
              onSpeak = { viewModel.speakCurrentSentence() },
              onClear = { viewModel.clearSentence() },
              onDeleteLast = { viewModel.deleteLastToken() },
              onRemoveTokenAt = { viewModel.removeTokenAt(it) },
              onCopySentence = onCopySentence,
              onShareSentence = onShareSentence,
              onSelectCategory = { viewModel.setCategoryFilter(it) },
              onExitFolder = { viewModel.exitFolder() },
              onSearchQueryChange = { viewModel.setSearchQuery(it) },
              onAddCustomTile = { viewModel.addCustomTile(it) },
              onDeleteTile = { viewModel.deleteTile(it) }
            )

            1 -> QuickChatScreen(
              quickPhrases = quickPhrases,
              favoritePhraseIds = favoritePhraseIds,
              isCviMode = settings.highContrastCvi,
              onSpeakPhrase = { viewModel.speakText(it) },
              onToggleFavorite = { viewModel.toggleFavoritePhrase(it) },
              onAppendToSentence = { viewModel.appendTextToSentence(it) }
            )

            2 -> TypeToSpeakScreen(
              isSpeaking = isSpeaking,
              onSpeakText = { viewModel.speakText(it) },
              onAppendToSentence = { viewModel.appendTextToSentence(it) }
            )

            3 -> SettingsScreen(
              settings = settings,
              history = history,
              customTiles = customTiles,
              onUpdateSettings = { viewModel.updateSettings(it) },
              onTestVoice = { viewModel.testVoice() },
              onSpeakHistoryItem = { viewModel.speakText(it) },
              onDeleteHistoryItem = { viewModel.deleteHistoryItem(it) },
              onDeleteCustomTile = { viewModel.deleteCustomTile(it) },
              onReplayStartup = onReplayStartup
            )
          }
        }
      }
    }
  }
}
