package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util

import androidx.room.Index

sealed class SortType(val orderType: OrderType) {
    class Title(orderType: OrderType): SortType(orderType)
    class Date(orderType: OrderType): SortType(orderType)
    class Colour(orderType: OrderType): SortType(orderType)

    fun changeOrderType(orderType: OrderType): SortType {
        return when(this) {
            is Title -> SortType.Title(orderType)
            is Date -> SortType.Date(orderType)
            is Colour -> SortType.Colour(orderType)
        }
    }
}

