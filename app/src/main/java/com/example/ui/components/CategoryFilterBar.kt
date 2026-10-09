package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FitzgeraldCategory

@Composable
fun CategoryFilterBar(
  selectedCategory: FitzgeraldCategory?,
  currentFolderName: String?,
  onSelectCategory: (FitzgeraldCategory?) -> Unit,
  onExitFolder: () -> Unit,
  isCviMode: Boolean,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxWidth()) {
    // If inside a subfolder, show a prominent breadcrumb banner
    if (currentFolderName != null) {
      Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 8.dp)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Folder,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onPrimaryContainer,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = currentFolderName,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
              )
            )
          }

          TextButton(
            onClick = onExitFolder,
            colors = ButtonDefaults.textButtonColors(
              contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = null,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              "Back to Core",
              style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
      }
    }

    // Description above category tabs (centered and spaced out)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp, bottom = 10.dp),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Default.Delete,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
        modifier = Modifier.size(16.dp)
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = "Long click to delete card.",
        style = MaterialTheme.typography.bodyMedium.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontWeight = FontWeight.Medium,
          fontSize = 13.sp,
          letterSpacing = 0.4.sp
        )
      )
    }

    // Horizontal category chips
    val scrollState = rememberScrollState()
    val orderedCategories = listOf(
      FitzgeraldCategory.CUSTOM,
      FitzgeraldCategory.PRONOUN_PEOPLE,
      FitzgeraldCategory.ACTION_VERB,
      FitzgeraldCategory.DESCRIPTOR_ADJECTIVE,
      FitzgeraldCategory.NOUN_OBJECT,
      FitzgeraldCategory.SOCIAL_ETIQUETTE,
      FitzgeraldCategory.PREPOSITION_GLUE,
      FitzgeraldCategory.QUESTION_NEGATION
    )

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(scrollState),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // "All" chip
      FilterChip(
        selected = selectedCategory == null,
        onClick = { onSelectCategory(null) },
        label = {
          Text(
            text = "All Core",
            style = MaterialTheme.typography.labelLarge.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            )
          )
        },
        colors = FilterChipDefaults.filterChipColors(
          selectedContainerColor = MaterialTheme.colorScheme.primary,
          selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
          containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
          labelColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.testTag("chip_cat_all")
      )

      // Ordered Fitzgerald Categories with "Custom" right after "All Core"
      orderedCategories.forEach { category ->
        val isSelected = selectedCategory == category
        val chipBorderColor = category.getBorderColor(isCviMode)
        val chipBgColor = if (isSelected) category.getBackgroundColor(isCviMode) else MaterialTheme.colorScheme.surfaceContainerHigh
        val chipTextColor = if (isSelected) category.getTextColor(isCviMode) else MaterialTheme.colorScheme.onSurface

        FilterChip(
          selected = isSelected,
          onClick = {
            onSelectCategory(if (isSelected) null else category)
          },
          label = {
            Text(
              text = category.displayName,
              style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
              )
            )
          },
          border = if (isSelected) BorderStroke(2.dp, chipBorderColor) else null,
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = chipBgColor,
            selectedLabelColor = chipTextColor,
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            labelColor = MaterialTheme.colorScheme.onSurface
          ),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.testTag("chip_cat_${category.name}")
        )
      }
    }
  }
}
