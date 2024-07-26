package com.example.todolistyandex.ui.compose.listoftask

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel

@Composable
fun TaskListContent(
    tasks: List<ToDoItem>,
    showCompletedTasks: Boolean,
    completedTaskCount: Int,
    scrollState: LazyListState,
    navController: NavController,
    taskViewModel: ListOfTaskViewModel,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        val textSize by remember {
            derivedStateOf {
                if (scrollState.firstVisibleItemScrollOffset > 0) 20.sp else 32.sp
            }
        }
        val showAdditionalText by remember {
            derivedStateOf {
                scrollState.firstVisibleItemScrollOffset == 0
            }
        }

        val completedTaskNames = tasks.filter { it.completeFlag }.map { it.text }

        Header(
            textSize = textSize,
            showAdditionalText = showAdditionalText,
            completedTaskCount = completedTaskCount,
            showCompletedTasks = showCompletedTasks,
            taskViewModel = taskViewModel,
            completedTaskNames = completedTaskNames
        )

        TaskList(
            tasks = tasks,
            scrollState = scrollState,
            taskViewModel = taskViewModel,
            navController = navController
        )
    }
}