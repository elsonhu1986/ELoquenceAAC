package com.example.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.*

enum class FitzgeraldCategory(
  val displayName: String,
  val lightBg: Color,
  val lightBorder: Color,
  val lightAccent: Color,
  val cviBorder: Color
) {
  PRONOUN_PEOPLE(
    displayName = "People & Pronouns",
    lightBg = FzPronounBg,
    lightBorder = FzPronounBorder,
    lightAccent = FzPronounAccent,
    cviBorder = CviPronounBorder
  ),
  ACTION_VERB(
    displayName = "Actions & Verbs",
    lightBg = FzActionBg,
    lightBorder = FzActionBorder,
    lightAccent = FzActionAccent,
    cviBorder = CviActionBorder
  ),
  DESCRIPTOR_ADJECTIVE(
    displayName = "Descriptors & Feelings",
    lightBg = FzDescriptorBg,
    lightBorder = FzDescriptorBorder,
    lightAccent = FzDescriptorAccent,
    cviBorder = CviDescriptorBorder
  ),
  NOUN_OBJECT(
    displayName = "Nouns & Objects",
    lightBg = FzNounBg,
    lightBorder = FzNounBorder,
    lightAccent = FzNounAccent,
    cviBorder = CviNounBorder
  ),
  SOCIAL_ETIQUETTE(
    displayName = "Social & Manners",
    lightBg = FzSocialBg,
    lightBorder = FzSocialBorder,
    lightAccent = FzSocialAccent,
    cviBorder = CviSocialBorder
  ),
  PREPOSITION_GLUE(
    displayName = "Prepositions & Core",
    lightBg = FzPrepositionBg,
    lightBorder = FzPrepositionBorder,
    lightAccent = FzPrepositionAccent,
    cviBorder = CviPrepositionBorder
  ),
  QUESTION_NEGATION(
    displayName = "Questions & Negation",
    lightBg = FzQuestionBg,
    lightBorder = FzQuestionBorder,
    lightAccent = FzQuestionAccent,
    cviBorder = CviQuestionBorder
  ),
  CUSTOM(
    displayName = "Custom",
    lightBg = FzCustomBg,
    lightBorder = FzCustomBorder,
    lightAccent = FzCustomAccent,
    cviBorder = CviCustomBorder
  );

  fun getBackgroundColor(isCviMode: Boolean): Color {
    return if (isCviMode) CviSurface else lightBg
  }

  fun getBorderColor(isCviMode: Boolean): Color {
    return if (isCviMode) cviBorder else lightBorder
  }

  fun getTextColor(isCviMode: Boolean): Color {
    return if (isCviMode) CviText else lightAccent
  }
}
