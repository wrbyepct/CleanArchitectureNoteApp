package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plcoding.cleanarchitecturenoteapp.ui.theme.*
import java.lang.Exception


@Entity(tableName = "note")
data class Note(
    @PrimaryKey
    var id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val colour: Int
) {
    companion object {
        val noteColours = listOf(RedOrange, RedPink, BabyBlue, LightGreen, Violet)
    }
}


class InvalidNoteException(message: String) : Exception(message)




