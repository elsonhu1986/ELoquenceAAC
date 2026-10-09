package com.example.ui

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AACPreferences
import com.example.data.VocabularyData
import com.example.model.AACTile
import com.example.model.FitzgeraldCategory
import com.example.model.QuickPhrase
import com.example.model.SentenceHistoryItem
import com.example.model.SpeechSettings
import com.example.speech.TextToSpeechManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

class AACViewModel(application: Application) : AndroidViewModel(application) {

  private val prefs = AACPreferences(application)
  val ttsManager = TextToSpeechManager(application)

  private val _tokens = MutableStateFlow<List<AACTile>>(emptyList())
  val tokens: StateFlow<List<AACTile>> = _tokens.asStateFlow()

  private val _currentFolderId = MutableStateFlow<String?>(null)
  val currentFolderId: StateFlow<String?> = _currentFolderId.asStateFlow()

  private val _selectedCategory = MutableStateFlow<FitzgeraldCategory?>(null)
  val selectedCategory: StateFlow<FitzgeraldCategory?> = _selectedCategory.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _customTiles = MutableStateFlow(prefs.getCustomTiles())
  val customTiles: StateFlow<List<AACTile>> = _customTiles.asStateFlow()

  private val _quickPhrases = MutableStateFlow(VocabularyData.defaultQuickPhrases)
  val quickPhrases: StateFlow<List<QuickPhrase>> = _quickPhrases.asStateFlow()

  private val _favoritePhraseIds = MutableStateFlow(prefs.getFavoritePhrases())
  val favoritePhraseIds: StateFlow<Set<String>> = _favoritePhraseIds.asStateFlow()

  private val _history = MutableStateFlow(prefs.getHistory())
  val history: StateFlow<List<SentenceHistoryItem>> = _history.asStateFlow()

  private val _settings = MutableStateFlow(prefs.getSettings())
  val settings: StateFlow<SpeechSettings> = _settings.asStateFlow()

  private val _activeTab = MutableStateFlow(0)
  val activeTab: StateFlow<Int> = _activeTab.asStateFlow()

  val isSpeaking: StateFlow<Boolean> = ttsManager.isSpeaking

  init {
    val s = _settings.value
    ttsManager.setSpeechRate(s.rate)
    ttsManager.setPitch(s.pitch)
  }

  private val _deletedTileIds = MutableStateFlow(prefs.getDeletedTileIds())
  val deletedTileIds: StateFlow<Set<String>> = _deletedTileIds.asStateFlow()

  // Combined and filtered visible tiles for the grid
  val visibleTiles: StateFlow<List<AACTile>> = combine(
    _currentFolderId,
    _selectedCategory,
    _searchQuery,
    _customTiles,
    _deletedTileIds
  ) { folderId, category, query, custom, deletedIds ->
    val all = VocabularyData.defaultTiles + custom
    all.filter { tile ->
      if (deletedIds.contains(tile.id)) return@filter false
      val matchesFolder = if (query.isNotBlank()) true else (tile.folderId == folderId)
      val matchesCategory = when (category) {
        null -> true
        FitzgeraldCategory.CUSTOM -> (tile.category == FitzgeraldCategory.CUSTOM || tile.isCustom)
        else -> tile.category == category
      }
      val matchesQuery = query.isBlank() || tile.label.contains(query, ignoreCase = true) || tile.spokenText.contains(query, ignoreCase = true)
      matchesFolder && matchesCategory && matchesQuery
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), VocabularyData.defaultTiles)

  fun onTileClicked(tile: AACTile) {
    triggerHaptic()

    // If folder, navigate into folder
    if (tile.isFolder) {
      _currentFolderId.value = tile.targetFolderId
      return
    }

    // Add to sentence
    _tokens.value = _tokens.value + tile

    // If speakOnTap is enabled, pronounce this single tile immediately
    if (_settings.value.speakOnTap) {
      ttsManager.speak(tile.spokenText)
    }
  }

  fun removeTokenAt(index: Int) {
    triggerHaptic()
    val list = _tokens.value.toMutableList()
    if (index in list.indices) {
      list.removeAt(index)
      _tokens.value = list
    }
  }

  fun deleteLastToken() {
    triggerHaptic()
    if (_tokens.value.isNotEmpty()) {
      _tokens.value = _tokens.value.dropLast(1)
    }
  }

  fun clearSentence() {
    triggerHaptic()
    _tokens.value = emptyList()
  }

  fun speakCurrentSentence() {
    if (isSpeaking.value) {
      ttsManager.stop()
      return
    }

    val currentTokens = _tokens.value
    if (currentTokens.isEmpty()) return

    triggerHaptic()
    val fullSentence = currentTokens.joinToString(" ") { it.spokenText }
    ttsManager.speak(fullSentence)

    // Save to history
    saveSentenceToHistory(fullSentence)

    if (_settings.value.clearAfterSpeak) {
      _tokens.value = emptyList()
    }
  }

  fun speakText(text: String) {
    if (text.isBlank()) return
    triggerHaptic()
    ttsManager.speak(text)
    saveSentenceToHistory(text)
  }

  fun appendTextToSentence(phraseText: String) {
    triggerHaptic()
    val tile = AACTile(
      id = "quick_${UUID.randomUUID()}",
      label = phraseText,
      spokenText = phraseText,
      category = FitzgeraldCategory.SOCIAL_ETIQUETTE,
      iconName = "chat"
    )
    _tokens.value = _tokens.value + tile
  }

  private fun saveSentenceToHistory(sentence: String) {
    val newItem = SentenceHistoryItem(
      id = "hist_${UUID.randomUUID()}",
      sentence = sentence,
      timestamp = System.currentTimeMillis()
    )
    val updated = listOf(newItem) + _history.value.filter { it.sentence != sentence }
    _history.value = updated
    prefs.saveHistory(updated)
  }

  fun openFolder(folderId: String) {
    _currentFolderId.value = folderId
  }

  fun exitFolder() {
    _currentFolderId.value = null
  }

  fun setCategoryFilter(category: FitzgeraldCategory?) {
    _selectedCategory.value = category
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun setActiveTab(tab: Int) {
    _activeTab.value = tab
  }

  fun addCustomTile(tile: AACTile) {
    val updated = _customTiles.value + tile
    _customTiles.value = updated
    prefs.saveCustomTiles(updated)
  }

  fun deleteTile(tile: AACTile) {
    triggerHaptic()
    val updatedDeleted = _deletedTileIds.value + tile.id
    _deletedTileIds.value = updatedDeleted
    prefs.saveDeletedTileIds(updatedDeleted)

    if (tile.isCustom) {
      deleteCustomTile(tile.id)
    }

    _tokens.value = _tokens.value.filter { it.id != tile.id }
  }

  fun deleteCustomTile(tileId: String) {
    val updated = _customTiles.value.filter { it.id != tileId }
    _customTiles.value = updated
    prefs.saveCustomTiles(updated)
  }

  fun toggleFavoritePhrase(phraseId: String) {
    val current = _favoritePhraseIds.value.toMutableSet()
    if (current.contains(phraseId)) {
      current.remove(phraseId)
    } else {
      current.add(phraseId)
    }
    _favoritePhraseIds.value = current
    prefs.saveFavoritePhrases(current)
  }

  fun deleteHistoryItem(id: String) {
    val updated = _history.value.filter { it.id != id }
    _history.value = updated
    prefs.saveHistory(updated)
  }

  fun updateSettings(newSettings: SpeechSettings) {
    _settings.value = newSettings
    prefs.saveSettings(newSettings)
    ttsManager.setSpeechRate(newSettings.rate)
    ttsManager.setPitch(newSettings.pitch)
  }

  fun testVoice() {
    ttsManager.speak("Hello! ELoquence AAC is configured and ready to communicate.")
  }

  private fun triggerHaptic() {
    if (!_settings.value.hapticFeedback) return
    try {
      val ctx = getApplication<Application>()
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = ctx.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        vibratorManager?.defaultVibrator?.vibrate(
          VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
        )
      } else {
        @Suppress("DEPRECATION")
        val vibrator = ctx.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          vibrator?.vibrate(VibrationEffect.createOneShot(20, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
          @Suppress("DEPRECATION")
          vibrator?.vibrate(20)
        }
      }
    } catch (_: Exception) {
      // Haptics optional fallback
    }
  }

  override fun onCleared() {
    super.onCleared()
    ttsManager.shutdown()
  }
}
