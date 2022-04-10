package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.viewmodel_utils

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.SortType
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val sortType: SortType = SortType.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
