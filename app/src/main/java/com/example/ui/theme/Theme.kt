package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
  primary = EloquencePrimary,
  onPrimary = EloquenceOnPrimary,
  primaryContainer = EloquencePrimaryContainer,
  onPrimaryContainer = EloquenceOnPrimaryContainer,
  secondary = EloquenceSecondary,
  onSecondary = EloquenceOnSecondary,
  secondaryContainer = EloquenceSecondaryContainer,
  onSecondaryContainer = EloquenceOnSecondaryContainer,
  background = EloquenceBackground,
  onBackground = EloquenceOnBackground,
  surface = EloquenceSurface,
  onSurface = EloquenceOnSurface,
  surfaceVariant = EloquenceSurfaceContainerHigh,
  onSurfaceVariant = EloquenceOnSurfaceVariant,
  outline = EloquenceOutline,
  outlineVariant = EloquenceOutlineVariant,
  error = EloquenceError,
  onError = EloquenceOnError,
  errorContainer = EloquenceErrorContainer
)

private val DarkColorScheme = darkColorScheme(
  primary = EloquencePrimaryContainer,
  onPrimary = EloquenceOnPrimary,
  primaryContainer = EloquencePrimary,
  onPrimaryContainer = EloquenceOnPrimaryContainer,
  secondary = EloquenceSecondaryContainer,
  onSecondary = EloquenceOnSecondary,
  secondaryContainer = EloquenceSecondary,
  onSecondaryContainer = EloquenceOnSecondaryContainer,
  background = CviCanvasBg,
  onBackground = CviText,
  surface = CviSurface,
  onSurface = CviText,
  surfaceVariant = EloquencePrimaryContainer,
  onSurfaceVariant = EloquenceOutline,
  outline = EloquenceOutline,
  outlineVariant = EloquenceMuted,
  error = EloquenceError,
  onError = EloquenceOnError,
  errorContainer = EloquenceErrorContainer
)

@Composable
fun ELoquenceAACTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  highContrastCvi: Boolean = false,
  content: @Composable () -> Unit
) {
  val colorScheme = if (highContrastCvi || darkTheme) DarkColorScheme else LightColorScheme

  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as? Activity)?.window
      if (window != null) {
        window.statusBarColor = colorScheme.surface.toArgb()
        window.navigationBarColor = colorScheme.surface.toArgb()
        val insetsController = WindowCompat.getInsetsController(window, view)
        insetsController.isAppearanceLightStatusBars = !darkTheme && !highContrastCvi
        insetsController.isAppearanceLightNavigationBars = !darkTheme && !highContrastCvi
      }
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
