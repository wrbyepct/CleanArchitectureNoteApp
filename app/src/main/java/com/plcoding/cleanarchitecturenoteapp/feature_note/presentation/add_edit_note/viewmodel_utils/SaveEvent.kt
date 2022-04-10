package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.viewmodel_utils

sealed class SaveEvent {
    data class ShowSnackBar(val message: String): SaveEvent()
    object SaveNote: SaveEvent()
}