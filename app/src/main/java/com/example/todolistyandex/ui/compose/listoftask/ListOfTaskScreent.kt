package com.example.todolistyandex.ui.compose.listoftask

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todolistyandex.R
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        containerColor = CustomTheme.colors.backPrimary,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Tasks",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = CustomTheme.colors.labelPrimary
                        ),
                        modifier = Modifier
                            .clearAndSetSemantics {
                                contentDescription = "Список задач"
                            }
                    )
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate("settings") },
                        modifier = Modifier.clearAndSetSemantics {
                            contentDescription = "Кнопка настройки темы приложения"
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CustomTheme.colors.backPrimary,
                    titleContentColor = CustomTheme.colors.labelPrimary,
                    actionIconContentColor = CustomTheme.colors.labelPrimary,
                    navigationIconContentColor = CustomTheme.colors.labelPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("createNewTask/-1/ /NORMAL") },
                containerColor = CustomTheme.colors.colorBlue,
                contentColor = Color.White,
                shape = RoundedCornerShape(50),
                modifier = Modifier.clearAndSetSemantics {
                    contentDescription = "Кнопка создать новую задачу"
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = null
                )
            }
        },
        content = { paddingValues ->

            if (fetchError != null && tasks.isEmpty()) {
                ErrorScreen(
                    fetchError = fetchError,
                    retryAction = { taskViewModel.retryFetch() },
                    modifier = Modifier
                        .padding(paddingValues)
                        .clearAndSetSemantics {
                            contentDescription = "Ошибка загрузки задач"
                        }
                )
            } else {
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
    )
}
