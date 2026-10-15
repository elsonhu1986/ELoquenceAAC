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

enum class AACCardSize(
  val displayName: String,
  val subtitle: String,
  val baseHeightDp: Int,
  val iconSizeDp: Int,
  val fontSizeSp: Int,
  val paddingDp: Int
) {
  COMPACT(
    displayName = "Compact",
    subtitle = "Higher density • More cards on screen",
    baseHeightDp = 74,
    iconSizeDp = 24,
    fontSizeSp = 12,
    paddingDp = 4
  ),
  STANDARD(
    displayName = "Standard",
    subtitle = "Balanced layout • Recommended",
    baseHeightDp = 88,
    iconSizeDp = 30,
    fontSizeSp = 13,
    paddingDp = 6
  ),
  LARGE(
    displayName = "Large",
    subtitle = "Easier tapping • Prominent icons",
    baseHeightDp = 110,
    iconSizeDp = 38,
    fontSizeSp = 15,
    paddingDp = 8
  ),
  EXTRA_LARGE(
    displayName = "Extra Large",
    subtitle = "Maximum accessibility • Large touch targets",
    baseHeightDp = 136,
    iconSizeDp = 46,
    fontSizeSp = 17,
    paddingDp = 10
  );

  companion object {
    fun fromName(name: String?): AACCardSize {
      return entries.find { it.name.equals(name, ignoreCase = true) } ?: STANDARD
    }
  }
}

data class SpeechSettings(
  val pitch: Float = 1.0f,
  val rate: Float = 1.0f,
  val speakOnTap: Boolean = false,
  val clearAfterSpeak: Boolean = false,
  val hapticFeedback: Boolean = true,
  val highContrastCvi: Boolean = false,
  val gridColumns: Int = 4,
  val cardSize: AACCardSize = AACCardSize.STANDARD
)

data class SentenceHistoryItem(
  val id: String,
  val sentence: String,
  val timestamp: Long = System.currentTimeMillis(),
  val isFavorite: Boolean = false
)
