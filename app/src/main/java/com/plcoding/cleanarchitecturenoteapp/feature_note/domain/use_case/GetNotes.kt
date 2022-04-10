package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.SortType
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes (
    private val repository: NoteRepository
    ) {
    operator fun invoke(
        sortType: SortType = SortType.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (sortType.orderType) {
                is OrderType.Ascending -> {
                    when(sortType) {
                        is SortType.Title -> notes.sortedBy { it.title.lowercase() }
                        is SortType.Date -> notes.sortedBy { it.timestamp }
                        is SortType.Colour -> notes.sortedBy { it.colour }
                    }
                }
                is OrderType.Descending -> {
                    when (sortType) {
                        is SortType.Title -> notes.sortedByDescending { it.title }
                        is SortType.Date -> notes.sortedByDescending { it.timestamp }
                        is SortType.Colour -> notes.sortedByDescending { it.colour }
                    }
                }
            }
        }
    }
}