package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.SortType


@Composable
fun SortSection(
    modifier: Modifier = Modifier,
    sortType: SortType = SortType.Date(OrderType.Descending),
    onSortChange: (SortType) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = sortType is SortType.Title,
                onCheck = { onSortChange(SortType.Title(sortType.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = sortType is SortType.Date,
                onCheck = { onSortChange(SortType.Date(sortType.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Colour",
                selected = sortType is SortType.Colour,
                onCheck = { onSortChange(SortType.Colour(sortType.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = sortType.orderType is OrderType.Ascending,
                onCheck = {
                    onSortChange(sortType.changeOrderType(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = sortType.orderType is OrderType.Descending,
                onCheck = {
                    onSortChange(sortType.changeOrderType((OrderType.Descending)))
                }
            )
        }
    }

}