package com.example.todolistyandex.ui.compose.listoftask

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolistyandex.R
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel

@Composable
fun TaskList(
    tasks: List<ToDoItem>,
    scrollState: LazyListState,
    taskViewModel: ListOfTaskViewModel,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 18.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = CustomTheme.colors.backSecondary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                state = scrollState
            ) {
                items(tasks, key = { task -> task.id }) { task ->
                    TaskItem(
                        task = task,
                        onClick = {
                            navController.navigate("createNewTask/${task.id}/${task.text}/${task.priority}")
                        },
                        taskViewModel = taskViewModel,
                        navController = navController
                    )
                }
            }
            TextButton(
                onClick = { navController.navigate("createNewTask/-1/ /NORMAL") },
                modifier = Modifier.padding(start = 64.dp, top = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.new_task),
                    color = CustomTheme.colors.labelSecondary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
