package com.example.todolistyandex.ui.fragment

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.todolistyandex.data.settings.ThemePreference
import com.example.todolistyandex.ui.compose.listoftask.ListOfTaskScreen
import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.data.repository.ToDoItemsRepository
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class PreviewRepository : ToDoItemsRepository(FakeToDoItemDao()) {
    private val _tasks = MutableStateFlow(
        listOf(
            ToDoItem(
                id = 1,
                text = "Task 1",
                completeFlag = false,
                priority = ListOfTaskStatus.NORMAL,
                deadlineComplete = "12.07.2024"
            ),
            ToDoItem(
                id = 2,
                text = "Task 2",
                completeFlag = true,
                priority = ListOfTaskStatus.HIGH,
                deadlineComplete = "13.07.2024"
            ),
            ToDoItem(
                id = 3,
                text = "Task 3",
                completeFlag = false,
                priority = ListOfTaskStatus.LOW,
                deadlineComplete = "14.07.2024"
            )
        )
    )

    override fun getTodoItemById(id: Int): Flow<ToDoItem?> {
        return _tasks.map { items -> items.find { it.id == id } }
    }

    override suspend fun addOrUpdateTodoItem(item: ToDoItem): ToDoItem {
        return item
    }

    override suspend fun updateTaskCompletion(id: Int, isCompleted: Boolean) {
    }
}


@Composable
fun PreviewListOfTaskScreen() {
    val navController = rememberNavController()
    val taskViewModel = ListOfTaskViewModel(repository = PreviewRepository())
    ListOfTaskScreen(taskViewModel = taskViewModel, navController = navController)
}

@Preview(showBackground = true)
@Composable
fun LightThemeListOfTaskScreenPreview() {
    CustomTheme(themePreference = ThemePreference.LIGHT) {
        PreviewListOfTaskScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DarkThemeListOfTaskScreenPreview() {
    CustomTheme(themePreference = ThemePreference.DARK) {
        PreviewListOfTaskScreen()
    }
}