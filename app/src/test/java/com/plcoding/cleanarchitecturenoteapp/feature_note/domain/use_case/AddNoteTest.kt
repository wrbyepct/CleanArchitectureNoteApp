package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.plcoding.cleanarchitecturenoteapp.core.INVALID_TITLE
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.feature_note.data.repository.FakeRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class AddNoteTest {
    val fakeRepo = FakeRepository()
    val addNote = AddNote(fakeRepo)

    @Test
    fun `title is blank throws InvalidNoteException`() {
        val blankTitleNote = Note(
            title = "",
            content = "dsf",
            timestamp = 32L,
            colour = 324
        )
        runBlocking {
            try {
                addNote(blankTitleNote)
                assert(false) { "The note get inserted!" }
            } catch (e: InvalidNoteException) {
                assertThat(e.javaClass.simpleName).isEqualTo(InvalidNoteException::class.simpleName)
            }
        }
    }

    @Test
    fun `blank content throws InvalidNoteException`() {
        val blankContentNote = Note(
            title = "foo",
            content = "",
            timestamp = 32L,
            colour = 324
        )
        runBlocking {
            try {
                addNote(blankContentNote)
                assert(false) { "The note get inserted!" }
            } catch (e: InvalidNoteException) {
                assertThat(e.javaClass.simpleName).isEqualTo(InvalidNoteException::class.simpleName)
            }
        }
    }

    @Test
    fun `valid note inserted`() {
        val blankContentNote = Note(
            title = "foo",
            content = "sdfsd",
            timestamp = 32L,
            colour = 23
        )
        runBlocking {
            try {
                addNote(blankContentNote)
                assert(true) { "The note get inserted!" }
            } catch (e: InvalidNoteException) {
                assert(false) {e.message ?: "Unknown error"}
            }
        }
    }

}