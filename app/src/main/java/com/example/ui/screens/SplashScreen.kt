package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier
) {
  val alphaAnim = remember { Animatable(0f) }
  val scaleAnim = remember { Animatable(0.92f) }
  val textAlphaAnim = remember { Animatable(0f) }

  LaunchedEffect(Unit) {
    // Animate logo entrance preserving exact sizing ratio
    alphaAnim.animateTo(
      targetValue = 1f,
      animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
    )
    scaleAnim.animateTo(
      targetValue = 1f,
      animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
    )
    // Animate "Developed by ELvolution" at bottom
    textAlphaAnim.animateTo(
      targetValue = 1f,
      animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
    )

    // Hold for full brand viewing experience, then transition
    delay(1500)
    onDismiss()
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(Color.Black)
      .clickable(onClick = onDismiss) // Allow immediate skip on tap
      .testTag("startup_splash_screen")
  ) {
    // Centered Brand Logo with exact 1:1 sizing ratio preserved without clipping
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.Center)
        .padding(horizontal = 24.dp),
      contentAlignment = Alignment.Center
    ) {
      Image(
        painter = painterResource(id = R.drawable.brand_eloquence_exact_1791443968399),
        contentDescription = "ELoquence Brand Logo",
        contentScale = ContentScale.Fit,
        modifier = Modifier
          .fillMaxWidth(0.85f)
          .aspectRatio(1f)
          .scale(scaleAnim.value)
          .alpha(alphaAnim.value)
      )
    }

    // Bottom "Developed by ELvolution" Branding
    Column(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(bottom = 44.dp)
        .alpha(textAlphaAnim.value),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "Developed by ELvolution",
        style = MaterialTheme.typography.titleMedium.copy(
          fontFamily = FontFamily.SansSerif,
          fontWeight = FontWeight.SemiBold,
          fontSize = 17.sp,
          letterSpacing = 0.5.sp
        ),
        color = Color(0xFFD4AF37), // Metallic warm gold matching logo
        textAlign = TextAlign.Center
      )
    }
  }
}
