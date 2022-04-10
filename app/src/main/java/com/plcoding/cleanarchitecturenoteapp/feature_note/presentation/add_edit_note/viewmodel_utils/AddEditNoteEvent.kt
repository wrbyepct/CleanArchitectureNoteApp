package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.viewmodel_utils

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class TitleEntered(val title: String): AddEditNoteEvent()
    data class ContentEntered(val content: String): AddEditNoteEvent()
    data class TitleFocused(val focusState: FocusState): AddEditNoteEvent()
    data class ContentFocused(val focusState: FocusState): AddEditNoteEvent()
    data class ColorChanged(val color: Int): AddEditNoteEvent()
    object OnSaveNote: AddEditNoteEvent()
}