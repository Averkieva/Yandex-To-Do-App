package com.example.todolistyandex.ui.compose.createnewtask


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.todolistyandex.R
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.delay

@Composable
fun DeleteButton(taskText: String, taskViewModel: TaskViewModel, navController: NavController) {
    val deleteColor = if (taskText.isEmpty()) CustomTheme.colors.labelDisable else Color.Red
    val interactionSource = remember { MutableInteractionSource() }
    val showSnackbar = remember { mutableStateOf(false) }

    if (showSnackbar.value) {
        CountdownSnackbar(
            message = "Удалить задачу $taskText",
            onUndo = {
                showSnackbar.value = false
            },
            onDismiss = {
                taskViewModel.deleteTodoItem()
                navController.popBackStack()
            }
        )
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (deleteButton, deleteText) = createRefs()

        val deleteClickAction = {
            showSnackbar.value = true
        }

        IconButton(
            onClick = deleteClickAction,
            modifier = Modifier
                .constrainAs(deleteButton) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clearAndSetSemantics {
                    contentDescription = if (taskText.isNotEmpty()) {
                        "Удалить задачу $taskText"
                    } else {
                        "Удалить задачу нельзя, необходимо название задачи для ее удаления"
                    }
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(bounded = false, color = Color.Red)
                ) {}
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = deleteColor
            )
        }

        Text(
            text = stringResource(R.string.delete),
            color = deleteColor,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .constrainAs(deleteText) {
                    start.linkTo(deleteButton.end, margin = 8.dp)
                    top.linkTo(deleteButton.top)
                    bottom.linkTo(deleteButton.bottom)
                }
                .clearAndSetSemantics {
                    contentDescription = if (taskText.isNotEmpty()) {
                        "Удалить задачу $taskText"
                    } else {
                        "Удалить задачу нельзя, необходимо название задачи для ее удаления"
                    }
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(bounded = true, color = deleteColor),
                    onClick = deleteClickAction
                )
        )
    }
}

@Composable
fun CountdownSnackbar(
    message: String,
    onUndo: () -> Unit,
    onDismiss: () -> Unit,
    countdownTime: Int = 5
) {
    val scaffoldState = rememberScaffoldState()
    val countdown = remember { mutableStateOf(countdownTime) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarVisible = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (countdown.value > 0) {
            delay(1000L)
            countdown.value -= 1
        }
        if (countdown.value == 0) {
            onDismiss()
            snackbarVisible.value = false
        }
    }

    AnimatedVisibility(
        visible = snackbarVisible.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        Snackbar(
            action = {
                TextButton(
                    onClick = {
                        onUndo()
                        snackbarVisible.value = false
                    },
                    modifier = Modifier.semantics {
                        contentDescription = "Кнопка отменить"
                    }
                ) {
                    Text(
                        text = "Отменить",
                        color = CustomTheme.colors.colorBlue,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            containerColor = CustomTheme.colors.backPrimary,
            modifier = Modifier
                .padding(8.dp)
                .semantics {
                    contentDescription = "$message (${countdown.value} секунд)"
                }
        ) {
            Text(
                text = "$message (${countdown.value})",
                color = CustomTheme.colors.labelPrimary,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}


