package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case


import com.google.common.truth.Truth.assertThat
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.feature_note.data.repository.FakeRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.SortType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.random.Random


/**
 *
 */
class GetNotesTest {

    /**
     *  What do we need for GetNote test?
     */
    // Fake repo
    private val fakeRepo = FakeRepository()
    // I don't know if we declare the it later or initiate right now
    private val getNotes = GetNotes(fakeRepo)
    private val notesToInsert = mutableListOf<Note>()
    @Before
    fun setUp() = runBlocking {
        // Create fake data and insert it
        ('a'..'z').forEachIndexed { index, c ->
           delay(1L)
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = Random.nextLong(),
                    colour = Random.nextInt(),
                )
            )
        }
        notesToInsert.shuffle()
        notesToInsert.forEach {
            fakeRepo.insertNote(it)
        }
    }

    /**
     *  Title  order
     */
    @Test
    fun `Get notes, sorted by title in ascending order, correct`() = runBlocking {
         val notes = getNotes(SortType.Title(OrderType.Ascending)).first()
         for (i in 0 until notes.size - 1) {
             assertThat(notes[i].title).isLessThan(notes[i+1].title)
         }
    }
    @Test
    fun `Get notes, sorted by title in descending order, correct`() = runBlocking {
         val notes = getNotes(SortType.Title(OrderType.Descending)).first()
         for (i in 0 until notes.size - 1) {
             assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
         }
    }

    /**
     *  Date order
     */
    @Test
    fun `Get notes, sorted by date in ascending order, correct`() = runBlocking {
        val notes = getNotes(SortType.Date(OrderType.Ascending)).first()
        for (i in 0 until notes.size - 1) {
            assertThat(notes[i].timestamp).isLessThan(notes[i+1].timestamp)
        }
    }
    @Test
    fun `Get notes, sorted by date in descending order, correct`() = runBlocking {
         val notes = getNotes(SortType.Date(OrderType.Descending)).first()
         for (i in 0 until notes.size - 1) {
             assertThat(notes[i].timestamp).isGreaterThan(notes[i+1].timestamp)
         }
    }
    /**
     *  Color order
     */
    @Test
    fun `Get notes, sorted by color in ascending order, correct`() = runBlocking {
        val notes = getNotes(SortType.Colour(OrderType.Ascending)).first()
        for (i in 0 until notes.size - 1) {
            assertThat(notes[i].colour).isLessThan(notes[i+1].colour)
        }
    }
    @Test
    fun `Get notes, sorted by color in descending order, correct`() = runBlocking {
        val notes = getNotes(SortType.Colour(OrderType.Descending)).first()
        for (i in 0 until notes.size - 1) {
            assertThat(notes[i].colour).isGreaterThan(notes[i+1].colour)
        }
    }
}