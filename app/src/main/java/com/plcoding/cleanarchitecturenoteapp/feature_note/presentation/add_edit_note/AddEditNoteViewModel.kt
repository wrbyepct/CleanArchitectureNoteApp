package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.core.NEW_NOTE
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUseCases
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.viewmodel_utils.AddEditNoteEvent
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.viewmodel_utils.NoteTextField
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.viewmodel_utils.SaveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
   private val noteUseCases: NoteUseCases,
   // It contains navArgs
   savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * Set up component states
     */
    private val _noteTitle = mutableStateOf(
        NoteTextField(hint = "Enter title...")
    )
    val noteTitle: State<NoteTextField> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextField(hint = "Enter content...")
    )
    val noteContent: State<NoteTextField> = _noteContent
    
    private val _noteColor = mutableStateOf(Note.noteColours.random())
    val noteColor: State<Int> = _noteColor

    /**
     *  Why use sharedFlow for one time event?
     */
    private val _eventFlow = MutableSharedFlow<SaveEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {

        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            /**
             *  When select existing note
             */
            if (noteId != NEW_NOTE ) {

                viewModelScope.launch {
                    // Use .also to do sth which is return
                    noteUseCases.getSpecificNote(noteId)?.also { note ->
                        // We need current note id because we need to insert it to the db later
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.colour
                    }
                }
            }

        }
    }



    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.TitleEntered -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.title,
                )
            }
            is AddEditNoteEvent.TitleFocused -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ContentEntered -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.content,
                )
            }
            is AddEditNoteEvent.ContentFocused -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ColorChanged -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.OnSaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                colour = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(SaveEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            SaveEvent.ShowSnackBar(
                                message = "Couldn't save note"
                            )
                        )
                    }

                }
            }

        }
    }

}
