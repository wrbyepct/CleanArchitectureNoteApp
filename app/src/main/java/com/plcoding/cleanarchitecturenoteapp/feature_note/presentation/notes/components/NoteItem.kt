package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import android.widget.Space
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.max
import androidx.core.graphics.ColorUtils

@Composable
fun NoteItem(
    note: Note,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onDeleteNote: () -> Unit,
    onNoteClicked: () -> Unit
) {
    /**
     *  Note body design
    */
    Box(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onNoteClicked()
            }
    ) {
        /**
         * Use Canvas to draw customised shape
         */
        Canvas(
            // matchParentSize
            // Fill the size
            // Only after the Box's size is defined
            modifier = Modifier.matchParentSize()
        ) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            /**
             * Anything will be cut if outside clipPath
             */
            clipPath(clipPath) {
                /**
                 *  Draw note shape and cutCorner shape
                 */
                drawRoundRect(
                    size = size,
                    color = Color(note.colour),
                    cornerRadius = CornerRadius(cornerRadius.toPx())

                )
                drawRoundRect(
                    size = Size(cutCornerSize.toPx() + 10f, cutCornerSize.toPx() + 10f ),
                    color = Color(
                        ColorUtils.blendARGB(note.colour, 0x000000, 0.2f)
                    ),
                    cornerRadius = CornerRadius(cornerRadius.toPx()),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -10f)

                )
            }
        }
        /**
         * Note content container
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            /**
             *  Note title
             */
            Text(
                text = note.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            /**
             *  Note content
             */
            Text(
                text = note.content,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
        /**
         * Delete button
         */
        IconButton(
            onClick = onDeleteNote,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete note",
                tint = MaterialTheme.colors.onSurface
            )
        }

    }
}