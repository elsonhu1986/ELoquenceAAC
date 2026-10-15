package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.AACCardSize
import com.example.model.AACTile

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AACCard(
  tile: AACTile,
  isCviMode: Boolean = false,
  cardSize: AACCardSize = AACCardSize.STANDARD,
  onClick: () -> Unit,
  onLongClick: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val scale by animateFloatAsState(targetValue = if (isPressed) 0.94f else 1.0f, label = "card_scale")

  val bgColor = tile.category.getBackgroundColor(isCviMode)
  val borderColor = tile.category.getBorderColor(isCviMode)
  val textColor = tile.category.getTextColor(isCviMode)
  val icon = IconHelper.getIcon(tile.iconName)

  Surface(
    modifier = modifier
      .scale(scale)
      .fillMaxWidth()
      .height(cardSize.baseHeightDp.dp)
      .combinedClickable(
        interactionSource = interactionSource,
        indication = ripple(color = textColor.copy(alpha = 0.3f)),
        onClick = onClick,
        onLongClick = onLongClick
      )
      .testTag("aac_card_${tile.id}"),
    shape = RoundedCornerShape(if (cardSize == AACCardSize.EXTRA_LARGE) 16.dp else 12.dp),
    color = bgColor,
    border = BorderStroke(if (isCviMode) 2.5.dp else 2.dp, borderColor),
    shadowElevation = if (isPressed) 1.dp else 3.dp,
    tonalElevation = 2.dp
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = cardSize.paddingDp.dp, vertical = cardSize.paddingDp.dp)
    ) {
      // Subfolder indicator in top-right corner
      if (tile.isFolder) {
        Icon(
          imageVector = Icons.Default.Folder,
          contentDescription = "Folder",
          tint = textColor.copy(alpha = 0.85f),
          modifier = Modifier
            .size((cardSize.iconSizeDp / 2).coerceAtLeast(14).dp)
            .align(Alignment.TopEnd)
        )
      }

      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        if (tile.imageUri != null) {
          AsyncImage(
            model = tile.imageUri,
            contentDescription = tile.label,
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .size((cardSize.iconSizeDp + 4).dp)
              .clip(RoundedCornerShape(8.dp))
          )
        } else {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = textColor,
            modifier = Modifier.size(cardSize.iconSizeDp.dp)
          )
        }

        Spacer(modifier = Modifier.height(if (cardSize == AACCardSize.COMPACT) 2.dp else 4.dp))

        Text(
          text = tile.label,
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = cardSize.fontSizeSp.sp,
            letterSpacing = 0.01.sp
          ),
          color = textColor,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          textAlign = TextAlign.Center
        )
      }
    }
  }
}
