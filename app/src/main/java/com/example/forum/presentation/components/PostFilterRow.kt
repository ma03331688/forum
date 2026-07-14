package com.example.forum.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PostFilterRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        FilterChip(
            selected = true,
            onClick = { },
            label = {
                Text(
                    "LIFO",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.surface,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                containerColor = MaterialTheme.colorScheme.tertiary,
                labelColor = MaterialTheme.colorScheme.onTertiary
            ),
            shape = MaterialTheme.shapes.large,
            border = null
        )
    }
}