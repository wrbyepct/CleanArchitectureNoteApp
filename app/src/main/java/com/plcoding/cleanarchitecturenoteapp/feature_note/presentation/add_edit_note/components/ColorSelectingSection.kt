package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note


@Composable
fun ColorSelectingSection(
    onColorSelected: (Int) -> Unit,
    currentStoredNoteColor: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Note.noteColours.forEach { btnColor ->
            // Color circle
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(15.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color(btnColor))
                    .border(
                        width = 3.dp,
                        // Selected color btn should have black border
                        color = if (currentStoredNoteColor == btnColor) {
                            Color.Black
                        } else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable {
                        // Animate changed background color and update current selected color
                        onColorSelected(btnColor)
                    }
            )
        }
    }
}

