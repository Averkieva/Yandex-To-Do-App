package com.example.todolistyandex.ui.compose.listoftask

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel

@Composable
fun ListOfTaskScreen(
    taskViewModel: ListOfTaskViewModel = hiltViewModel(),
    navController: NavController
) {
    val tasks by taskViewModel.tasks.collectAsState(initial = emptyList())
    val showCompletedTasks by taskViewModel.showCompletedTasks.collectAsState()
    val completedTaskCount by taskViewModel.completedTaskCount.collectAsState(0)
    val scrollState = rememberLazyListState()
    val fetchError by taskViewModel.fetchError.collectAsState()

    if (fetchError != null && tasks.isEmpty()) {
        ErrorScreen(fetchError = fetchError, retryAction = { taskViewModel.retryFetch() })
    } else {
        TaskListScaffold(
            tasks = tasks,
            showCompletedTasks = showCompletedTasks,
            completedTaskCount = completedTaskCount,
            scrollState = scrollState,
            navController = navController,
            taskViewModel = taskViewModel
        )
    }
}