package com.example.todolistyandex.ui.fragment

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.navigation.compose.rememberNavController
import com.example.todolistyandex.data.settings.ThemePreference
import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.data.repository.ToDoItemsRepository
import com.example.todolistyandex.data.room.ToDoItemDao
import com.example.todolistyandex.data.room.ToDoItemEntity
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun PreviewCreateNewTaskScreenLight() {
    CustomTheme(themePreference = ThemePreference.LIGHT) {
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
    CustomTheme(themePreference = ThemePreference.DARK) {
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

class FakeTaskViewModel : TaskViewModel(ToDoItemsRepository(FakeToDoItemDao())) {
    override fun getTaskById(id: Int): LiveData<ToDoItem?> = liveData {
        emit(ToDoItem(id = id, text = "Фейковая задача", priority = ListOfTaskStatus.NORMAL))
    }
}


class FakeToDoItemDao : ToDoItemDao {
    private val items = mutableListOf<ToDoItemEntity>()

    override fun getTodoItems(): Flow<List<ToDoItemEntity>> {
        return flow {
            emit(items)
        }
    }

    override suspend fun addOrUpdateTodoItem(todoItem: ToDoItemEntity) {
        val index = items.indexOfFirst { it.id == todoItem.id }
        if (index >= 0) {
            items[index] = todoItem
        } else {
            items.add(todoItem)
        }
    }

    override suspend fun deleteTodoItem(todoItem: ToDoItemEntity) {
        items.remove(todoItem)
    }

    override suspend fun clearAll() {
        TODO("Not yet implemented")
    }
}

