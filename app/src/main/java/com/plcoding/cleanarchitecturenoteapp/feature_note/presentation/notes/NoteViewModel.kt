package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUseCases
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.SortType
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.viewmodel_utils.NoteEvent
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.viewmodel_utils.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    /**
     * Initial state
     */
    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(SortType.Date(OrderType.Descending))
    }

    /**
     * Handle event, call methods from use cases
     */
    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.Sort -> {
                if (state.value.sortType::class == event.sortType::class &&
                    state.value.sortType.orderType == event.sortType.orderType
                ) {
                    return
                }
                getNotes(event.sortType)
            }
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NoteEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(sortType: SortType) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(sortType)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    sortType = sortType
                )
            }
            .launchIn(viewModelScope)
    }
}