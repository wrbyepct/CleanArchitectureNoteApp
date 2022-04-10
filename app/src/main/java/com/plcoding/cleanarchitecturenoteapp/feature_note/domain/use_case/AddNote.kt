package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.core.INVALID_CONTENT
import com.plcoding.cleanarchitecturenoteapp.core.INVALID_TITLE
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNote(private val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException(INVALID_TITLE)
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException(INVALID_CONTENT)
        }
        repository.insertNote(note)
    }
}