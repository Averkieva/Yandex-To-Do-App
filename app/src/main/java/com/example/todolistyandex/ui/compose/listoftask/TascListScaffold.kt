package com.example.todolistyandex.ui.compose.listoftask

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolistyandex.R
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel

@Composable
fun TaskListScaffold(
    tasks: List<ToDoItem>,
    showCompletedTasks: Boolean,
    completedTaskCount: Int,
    scrollState: LazyListState,
    navController: NavController,
    taskViewModel: ListOfTaskViewModel,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("createNewTask/-1/ /NORMAL") },
                modifier = Modifier.padding(10.dp),
                containerColor = CustomTheme.colors.colorBlue,
                contentColor = Color.White,
                shape = RoundedCornerShape(50)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = stringResource(id = R.string.add_task)
                )
            }
        },
        containerColor = CustomTheme.colors.backPrimary
    ) { paddingValues ->
        TaskListContent(
            tasks = tasks,
            showCompletedTasks = showCompletedTasks,
            completedTaskCount = completedTaskCount,
            scrollState = scrollState,
            navController = navController,
            taskViewModel = taskViewModel,
            paddingValues = paddingValues
        )
    }
}