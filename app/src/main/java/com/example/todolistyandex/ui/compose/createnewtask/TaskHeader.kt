package com.example.todolistyandex.ui.compose.createnewtask

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.todolistyandex.R
import com.example.todolistyandex.ui.theme.BlueDark
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.viewmodel.TaskViewModel

@Composable
fun TaskHeader(navController: NavController, taskText: String, taskViewModel: TaskViewModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (closeButton, saveButton) = createRefs()

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .constrainAs(closeButton) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .clearAndSetSemantics {
                    contentDescription = "Кнопка вернуться на предыдущий экран"
                }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = CustomTheme.colors.labelPrimary
            )
        }

        TextButton(
            onClick = {
                if (taskText.isNotEmpty()) {
                    taskViewModel.setText(taskText)
                    taskViewModel.saveToDoItem()
                }
                navController.popBackStack()
            },
            enabled = taskText.isNotEmpty(),
            modifier = Modifier
                .constrainAs(saveButton) {
                    end.linkTo(parent.end)
                    top.linkTo(closeButton.top)
                    bottom.linkTo(closeButton.bottom)
                }
                .clearAndSetSemantics {
                    contentDescription = if (taskText.isNotEmpty()) {
                        "Сохранить задачу $taskText"
                    } else {
                        "Сохранить задачу нельзя, необходимо название задачи для ее сохранения"
                    }
                }
        ) {
            Text(
                text = stringResource(R.string.save_task),
                color = BlueDark,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}