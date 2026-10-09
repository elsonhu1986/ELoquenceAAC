package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.AACMainScreen
import com.example.ui.AACViewModel
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.ELoquenceAACTheme

class MainActivity : ComponentActivity() {

  private val viewModel: AACViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      var isStartupShowing by rememberSaveable { mutableStateOf(true) }
      val settings by viewModel.settings.collectAsStateWithLifecycle()

      ELoquenceAACTheme(highContrastCvi = settings.highContrastCvi) {
        Surface(modifier = Modifier.fillMaxSize()) {
          if (isStartupShowing) {
            SplashScreen(onDismiss = { isStartupShowing = false })
          } else {
            AACMainScreen(
              viewModel = viewModel,
              onReplayStartup = { isStartupShowing = true }
            )
          }
        }
      }
    }
  }
}
