package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AACPreferences
import com.example.data.VocabularyData
import com.example.model.AACTile
import com.example.model.FitzgeraldCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("ELoquence AAC", appName)
  }

  @Test
  fun `verify core vocabulary spans all fitzgerald categories`() {
    val tiles = VocabularyData.defaultTiles
    assertTrue("Vocabulary should have plenty of cards", tiles.size > 30)

    val categories = tiles.map { it.category }.toSet()
    assertTrue(categories.contains(FitzgeraldCategory.PRONOUN_PEOPLE))
    assertTrue(categories.contains(FitzgeraldCategory.ACTION_VERB))
    assertTrue(categories.contains(FitzgeraldCategory.DESCRIPTOR_ADJECTIVE))
    assertTrue(categories.contains(FitzgeraldCategory.NOUN_OBJECT))
    assertTrue(categories.contains(FitzgeraldCategory.SOCIAL_ETIQUETTE))
    assertTrue(categories.contains(FitzgeraldCategory.PREPOSITION_GLUE))
    assertTrue(categories.contains(FitzgeraldCategory.QUESTION_NEGATION))
  }

  @Test
  fun `verify Custom category exists and deleted tiles persist`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val prefs = AACPreferences(context)

    assertEquals("Custom", FitzgeraldCategory.CUSTOM.displayName)

    val customTile = AACTile(
      id = "test_custom_1",
      label = "My Custom Card",
      spokenText = "My Custom Card",
      category = FitzgeraldCategory.CUSTOM,
      isCustom = true
    )
    prefs.saveCustomTiles(listOf(customTile))
    val loadedCustom = prefs.getCustomTiles()
    assertEquals(1, loadedCustom.size)
    assertEquals(FitzgeraldCategory.CUSTOM, loadedCustom[0].category)

    // Test card deletion persistence
    prefs.saveDeletedTileIds(setOf("test_custom_1"))
    val deletedIds = prefs.getDeletedTileIds()
    assertTrue(deletedIds.contains("test_custom_1"))
  }

  @Test
  fun `verify card size setting saves and restores correctly`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val prefs = AACPreferences(context)

    // Initial default should be STANDARD
    val defaultSettings = prefs.getSettings()
    assertEquals(com.example.model.AACCardSize.STANDARD, defaultSettings.cardSize)

    // Update to LARGE
    val largeSettings = defaultSettings.copy(cardSize = com.example.model.AACCardSize.LARGE)
    prefs.saveSettings(largeSettings)
    val loadedLarge = prefs.getSettings()
    assertEquals(com.example.model.AACCardSize.LARGE, loadedLarge.cardSize)

    // Update to EXTRA_LARGE
    val xlSettings = defaultSettings.copy(cardSize = com.example.model.AACCardSize.EXTRA_LARGE)
    prefs.saveSettings(xlSettings)
    val loadedXl = prefs.getSettings()
    assertEquals(com.example.model.AACCardSize.EXTRA_LARGE, loadedXl.cardSize)

    // Update to COMPACT
    val compactSettings = defaultSettings.copy(cardSize = com.example.model.AACCardSize.COMPACT)
    prefs.saveSettings(compactSettings)
    val loadedCompact = prefs.getSettings()
    assertEquals(com.example.model.AACCardSize.COMPACT, loadedCompact.cardSize)
  }
}
