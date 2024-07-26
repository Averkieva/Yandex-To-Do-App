package com.example.todolistyandex.ui.compose.listoftask

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.todolistyandex.R
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel

@Composable
fun Header(
    textSize: TextUnit,
    showAdditionalText: Boolean,
    completedTaskCount: Int,
    showCompletedTasks: Boolean,
    taskViewModel: ListOfTaskViewModel,
    completedTaskNames: List<String>
) {
    val myBusinessText = stringResource(id = R.string.my_business)
    val doneText = stringResource(id = R.string.done, completedTaskCount)
    val toggleCompletedTasksText = stringResource(id = R.string.toggle_completed_tasks)

    val taskWord = getTaskWord(completedTaskCount)

    Text(
        text = myBusinessText,
        color = CustomTheme.colors.labelPrimary,
        style = MaterialTheme.typography.bodyLarge,
        fontSize = textSize,
        modifier = Modifier
            .padding(start = 60.dp, top = 82.dp)
            .clearAndSetSemantics {
                contentDescription = myBusinessText
            }
    )
    if (showAdditionalText) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = doneText,
                color = CustomTheme.colors.labelSecondary,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.clearAndSetSemantics {
                    contentDescription = "Выполнено $completedTaskCount $taskWord"
                }
            )
            Spacer(modifier = Modifier.weight(1f))

            val completedTaskNamesDescription = completedTaskNames.joinToString(", ")
            IconButton(
                onClick = { taskViewModel.toggleShowCompletedTasks() },
                modifier = Modifier.clearAndSetSemantics {
                    contentDescription = if (showCompletedTasks) {
                        "Кнопка скрыть выполненные задачи: $completedTaskNamesDescription"
                    } else {
                        "Кнопка показать все выполненные задачи"
                    }
                }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (showCompletedTasks) R.drawable.cell_eye else R.drawable.cell_eye_off
                    ),
                    contentDescription = null,
                    tint = CustomTheme.colors.colorBlue
                )
            }
        }
    }
}

fun getTaskWord(count: Int): String {
    return when {
        count % 10 == 1 && count % 100 != 11 -> "задача"
        count % 10 in 2..4 && (count % 100 !in 12..14) -> "задачи"
        else -> "задач"
    }
}
