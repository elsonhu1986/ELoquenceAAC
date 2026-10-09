package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.AACTile
import com.example.model.FitzgeraldCategory
import com.example.model.SentenceHistoryItem
import com.example.model.SpeechSettings
import org.json.JSONArray
import org.json.JSONObject

class AACPreferences(context: Context) {

  private val prefs: SharedPreferences =
    context.getSharedPreferences("eloquence_aac_prefs", Context.MODE_PRIVATE)

  companion object {
    private const val KEY_PITCH = "pref_pitch"
    private const val KEY_RATE = "pref_rate"
    private const val KEY_SPEAK_ON_TAP = "pref_speak_on_tap"
    private const val KEY_CLEAR_AFTER_SPEAK = "pref_clear_after_speak"
    private const val KEY_HAPTIC = "pref_haptic"
    private const val KEY_HIGH_CONTRAST_CVI = "pref_high_contrast_cvi"
    private const val KEY_GRID_COLUMNS = "pref_grid_columns"
    private const val KEY_CUSTOM_TILES = "pref_custom_tiles"
    private const val KEY_HISTORY = "pref_sentence_history"
    private const val KEY_FAVORITES = "pref_favorite_phrases"
    private const val KEY_DELETED_TILES = "pref_deleted_tiles"
  }

  fun getSettings(): SpeechSettings {
    return SpeechSettings(
      pitch = prefs.getFloat(KEY_PITCH, 1.0f),
      rate = prefs.getFloat(KEY_RATE, 1.0f),
      speakOnTap = prefs.getBoolean(KEY_SPEAK_ON_TAP, false),
      clearAfterSpeak = prefs.getBoolean(KEY_CLEAR_AFTER_SPEAK, false),
      hapticFeedback = prefs.getBoolean(KEY_HAPTIC, true),
      highContrastCvi = prefs.getBoolean(KEY_HIGH_CONTRAST_CVI, false),
      gridColumns = prefs.getInt(KEY_GRID_COLUMNS, 4)
    )
  }

  fun saveSettings(settings: SpeechSettings) {
    prefs.edit()
      .putFloat(KEY_PITCH, settings.pitch)
      .putFloat(KEY_RATE, settings.rate)
      .putBoolean(KEY_SPEAK_ON_TAP, settings.speakOnTap)
      .putBoolean(KEY_CLEAR_AFTER_SPEAK, settings.clearAfterSpeak)
      .putBoolean(KEY_HAPTIC, settings.hapticFeedback)
      .putBoolean(KEY_HIGH_CONTRAST_CVI, settings.highContrastCvi)
      .putInt(KEY_GRID_COLUMNS, settings.gridColumns)
      .apply()
  }

  fun getCustomTiles(): List<AACTile> {
    val jsonString = prefs.getString(KEY_CUSTOM_TILES, null) ?: return emptyList()
    val list = mutableListOf<AACTile>()
    try {
      val array = JSONArray(jsonString)
      for (i in 0 until array.length()) {
        val obj = array.getJSONObject(i)
        val categoryName = obj.optString("category", FitzgeraldCategory.NOUN_OBJECT.name)
        val category = try {
          FitzgeraldCategory.valueOf(categoryName)
        } catch (_: Exception) {
          FitzgeraldCategory.NOUN_OBJECT
        }
        list.add(
          AACTile(
            id = obj.getString("id"),
            label = obj.getString("label"),
            spokenText = obj.optString("spokenText", obj.getString("label")),
            category = category,
            iconName = obj.optString("iconName", "chat"),
            imageUri = if (obj.has("imageUri")) obj.getString("imageUri") else null,
            folderId = if (obj.has("folderId")) obj.getString("folderId") else null,
            targetFolderId = if (obj.has("targetFolderId")) obj.getString("targetFolderId") else null,
            isCustom = true
          )
        )
      }
    } catch (_: Exception) {
      // Return empty if corrupted
    }
    return list
  }

  fun saveCustomTiles(tiles: List<AACTile>) {
    val array = JSONArray()
    for (tile in tiles) {
      val obj = JSONObject().apply {
        put("id", tile.id)
        put("label", tile.label)
        put("spokenText", tile.spokenText)
        put("category", tile.category.name)
        put("iconName", tile.iconName)
        if (tile.imageUri != null) put("imageUri", tile.imageUri)
        if (tile.folderId != null) put("folderId", tile.folderId)
        if (tile.targetFolderId != null) put("targetFolderId", tile.targetFolderId)
        put("isCustom", true)
      }
      array.put(obj)
    }
    prefs.edit().putString(KEY_CUSTOM_TILES, array.toString()).apply()
  }

  fun getHistory(): List<SentenceHistoryItem> {
    val jsonString = prefs.getString(KEY_HISTORY, null) ?: return emptyList()
    val list = mutableListOf<SentenceHistoryItem>()
    try {
      val array = JSONArray(jsonString)
      for (i in 0 until array.length()) {
        val obj = array.getJSONObject(i)
        list.add(
          SentenceHistoryItem(
            id = obj.getString("id"),
            sentence = obj.getString("sentence"),
            timestamp = obj.optLong("timestamp", System.currentTimeMillis()),
            isFavorite = obj.optBoolean("isFavorite", false)
          )
        )
      }
    } catch (_: Exception) {
      // Ignored
    }
    return list
  }

  fun saveHistory(history: List<SentenceHistoryItem>) {
    val array = JSONArray()
    for (item in history.take(50)) { // limit to last 50
      val obj = JSONObject().apply {
        put("id", item.id)
        put("sentence", item.sentence)
        put("timestamp", item.timestamp)
        put("isFavorite", item.isFavorite)
      }
      array.put(obj)
    }
    prefs.edit().putString(KEY_HISTORY, array.toString()).apply()
  }

  fun getFavoritePhrases(): Set<String> {
    return prefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
  }

  fun saveFavoritePhrases(favorites: Set<String>) {
    prefs.edit().putStringSet(KEY_FAVORITES, favorites).apply()
  }

  fun getDeletedTileIds(): Set<String> {
    return prefs.getStringSet(KEY_DELETED_TILES, emptySet()) ?: emptySet()
  }

  fun saveDeletedTileIds(ids: Set<String>) {
    prefs.edit().putStringSet(KEY_DELETED_TILES, ids).apply()
  }
}
