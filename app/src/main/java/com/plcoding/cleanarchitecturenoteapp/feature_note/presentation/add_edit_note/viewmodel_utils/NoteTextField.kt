package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.viewmodel_utils

data class NoteTextField(
    val text: String = "",
    val hint: String = "",
    var isHintVisible: Boolean = true
)
