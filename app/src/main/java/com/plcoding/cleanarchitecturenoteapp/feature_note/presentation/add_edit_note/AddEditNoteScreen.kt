package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.components


import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.cleanarchitecturenoteapp.core.NEW_NOTE
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.viewmodel_utils.AddEditNoteEvent
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.viewmodel_utils.SaveEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {

    /**
     *  Component states
     */

    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != NEW_NOTE) noteColor else viewModel.noteColor.value)
        )
    }


    /**
     *  Snack bar and navigate up
     */
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SaveEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
                is SaveEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.OnSaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            /**
             *  Color selecting Section
             */

            ColorSelectingSection(
                onColorSelected = { selectedBtnColor: Int -> scope.launch {
                    noteBackgroundAnimatable.animateTo(
                        targetValue = Color(selectedBtnColor),
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    )
                }
                    viewModel.onEvent(AddEditNoteEvent.ColorChanged(selectedBtnColor)) },
                currentStoredNoteColor = viewModel.noteColor.value
            )

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * Note Title
             */
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.TitleEntered(it))
                },
                onFocusChange =  {
                    viewModel.onEvent(AddEditNoteEvent.TitleFocused(it))
                },
                isHintVisible = titleState.isHintVisible,
                textStyle = MaterialTheme.typography.h5,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * Note Content
             */
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.ContentEntered(it))
                },
                onFocusChange =  {
                    viewModel.onEvent(AddEditNoteEvent.ContentFocused(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )

        }

    }
}