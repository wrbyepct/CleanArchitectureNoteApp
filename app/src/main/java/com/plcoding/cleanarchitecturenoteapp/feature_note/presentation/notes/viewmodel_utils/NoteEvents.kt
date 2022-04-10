package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.viewmodel_utils

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.SortType

sealed class NoteEvent {
    data class Sort(val sortType: SortType): NoteEvent()
    data class DeleteNote(val note: Note): NoteEvent()
    object RestoreNote: NoteEvent()
    object ToggleOrderSection: NoteEvent()
}
