package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components.NoteItem
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components.SortSection
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.viewmodel_utils.NoteEvent
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch


@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddEditScreen.route) },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note"
                )
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Note",
                    style = MaterialTheme.typography.h4
                )

                IconButton(
                    onClick = {
                        viewModel.onEvent(NoteEvent.ToggleOrderSection)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort note",

                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                SortSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    sortType = state.sortType,
                    onSortChange = {
                        viewModel.onEvent(NoteEvent.Sort(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        onNoteClicked = {
                            navController.navigate(
                                route = Screen.AddEditScreen.route +
                                        "${note.id}&${note.colour}"
                            )
                        },
                        onDeleteNote = {
                            viewModel.onEvent(NoteEvent.DeleteNote(note))
                            // Show snack bar
                            scope.launch {
                                // It either returns ActionPerformed or ActionDismissed
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note deleted.",
                                    actionLabel = "Undo"
                                )
                                if (SnackbarResult.ActionPerformed == result) {
                                    viewModel.onEvent(NoteEvent.RestoreNote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}