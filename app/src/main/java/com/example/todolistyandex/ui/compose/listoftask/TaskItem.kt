package com.example.todolistyandex.ui.compose.listoftask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolistyandex.R
import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.theme.Gray
import com.example.todolistyandex.ui.theme.GreenDark
import com.example.todolistyandex.ui.theme.RedDark
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel

@Composable
fun TaskItem(
    task: ToDoItem,
    onClick: () -> Unit,
    taskViewModel: ListOfTaskViewModel,
    navController: NavController
) {
    val checkboxColor = if (task.completeFlag) GreenDark else when (task.priority) {
        ListOfTaskStatus.HIGH -> RedDark
        else -> Gray
    }

    val priorityIcon = when (task.priority) {
        ListOfTaskStatus.HIGH -> R.drawable.high_prioritet
        ListOfTaskStatus.LOW -> R.drawable.low_prioritet
        else -> null
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.completeFlag,
            onCheckedChange = {
                taskViewModel.updateTaskCompletion(task.id, it)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = checkboxColor,
                uncheckedColor = checkboxColor
            )
        )
        if (priorityIcon != null && !task.completeFlag) {
            Icon(
                painter = painterResource(id = priorityIcon),
                contentDescription = stringResource(id = R.string.high_priority),
                tint = if (task.priority == ListOfTaskStatus.HIGH) Color.Red else Color.Unspecified,
                modifier = Modifier.padding(end = 3.dp)
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        TaskTextContent(task = task)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                navController.navigate("createNewTask/${task.id}/${task.text}/${task.priority}")
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icons),
                contentDescription = stringResource(id = R.string.info)
            )
        }
    }
}
@Composable
fun TaskTextContent(task: ToDoItem) {
    Box(

    ) {
        Column {
            Text(
                text = task.text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = if (task.completeFlag) TextDecoration.LineThrough else TextDecoration.None
                ),
                color = if (task.completeFlag) Color.Gray else CustomTheme.colors.labelPrimary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            if (!task.deadlineComplete.isNullOrEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = stringResource(id = R.string.calendar),
                        tint = CustomTheme.colors.labelSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Дата: ${task.deadlineComplete}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}