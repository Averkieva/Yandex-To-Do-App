package com.example.todolistyandex.ui.fragment

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.navigation.compose.rememberNavController
import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.data.repository.ToDoItemsRepository
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.viewmodel.TaskViewModel

@Composable
fun PreviewCreateNewTaskScreenLight() {
    CustomTheme(darkTheme = false) {
        CreateNewTaskScreen(
            navController = rememberNavController(),
            taskViewModel = FakeTaskViewModel(),
            taskId = 1,
            taskTitle = "Заголовок задачи",
            taskPriority = ListOfTaskStatus.NORMAL.name
        )
    }
}

@Composable
fun PreviewCreateNewTaskScreenDark() {
    CustomTheme(darkTheme = true) {
        CreateNewTaskScreen(
            navController = rememberNavController(),
            taskViewModel = FakeTaskViewModel(),
            taskId = 1,
            taskTitle = "Заголовок задачи",
            taskPriority = ListOfTaskStatus.NORMAL.name
        )
    }
}

@Preview(name = "CreateNewTaskScreen Light Theme", showBackground = true)
@Composable
fun PreviewLightTheme() {
    PreviewCreateNewTaskScreenLight()
}

@Preview(name = "CreateNewTaskScreen Dark Theme", showBackground = true)
@Composable
fun PreviewDarkTheme() {
    PreviewCreateNewTaskScreenDark()
}

class FakeTaskViewModel : TaskViewModel(ToDoItemsRepository()) {
    override fun getTaskById(id: Int): LiveData<ToDoItem?> = liveData {
        emit(ToDoItem(id = id, text = "Фейковая задача", priority = ListOfTaskStatus.NORMAL))
    }
}
