package com.example.model

data class AACTile(
  val id: String,
  val label: String,
  val spokenText: String = label,
  val category: FitzgeraldCategory,
  val iconName: String = "chat",
  val imageUri: String? = null,
  val folderId: String? = null,
  val targetFolderId: String? = null,
  val isCustom: Boolean = false
) {
  val isFolder: Boolean
    get() = targetFolderId != null
}

data class QuickPhrase(
  val id: String,
  val title: String,
  val phrase: String,
  val categoryTag: String,
  val iconName: String,
  val isHighPriority: Boolean = false
)

data class SpeechSettings(
  val pitch: Float = 1.0f,
  val rate: Float = 1.0f,
  val speakOnTap: Boolean = false,
  val clearAfterSpeak: Boolean = false,
  val hapticFeedback: Boolean = true,
  val highContrastCvi: Boolean = false,
  val gridColumns: Int = 4
)

data class SentenceHistoryItem(
  val id: String,
  val sentence: String,
  val timestamp: Long = System.currentTimeMillis(),
  val isFavorite: Boolean = false
)
