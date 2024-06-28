package com.example.todolistyandex.data.repository

import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.data.model.ToDoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

open class ToDoItemsRepository {

    private val _todoItems = MutableStateFlow(initialTodoItems())
    private var currentMaxId = _todoItems.value.maxOfOrNull { it.id } ?: 0

    open val todoItems: StateFlow<List<ToDoItem>>
        get() = _todoItems

    open fun getTodoItemById(id: Int): Flow<ToDoItem?> {
        return todoItems.map { items -> items.find { it.id == id } }
    }

    open suspend fun addOrUpdateTodoItem(item: ToDoItem): ToDoItem {
        return withContext(Dispatchers.IO) {
            val currentList = _todoItems.value.toMutableList()
            val index = currentList.indexOfFirst { it.id == item.id }

            if (index >= 0) {
                currentList[index] = item
            } else {
                val newItem = item.copy(id = ++currentMaxId)
                currentList.add(newItem)
                _todoItems.value = currentList
                return@withContext newItem
            }

            _todoItems.value = currentList
            return@withContext item
        }
    }

    suspend fun updateTodoItem(item: ToDoItem) {
        withContext(Dispatchers.IO) {
            val currentList = _todoItems.value.toMutableList()
            val index = currentList.indexOfFirst { it.id == item.id }
            if (index >= 0) {
                currentList[index] = item
                _todoItems.value = currentList
            }
        }
    }

    open suspend fun deleteTodoItem(id: Int) {
        withContext(Dispatchers.IO) {
            val currentList = _todoItems.value.toMutableList()
            currentList.removeAll { it.id == id }
            _todoItems.value = currentList
        }
    }

    open suspend fun updateTaskCompletion(taskId: Int, isComplete: Boolean) {
        withContext(Dispatchers.IO) {
            val currentList = _todoItems.value.toMutableList()
            val index = currentList.indexOfFirst { it.id == taskId }
            if (index >= 0) {
                val item = currentList[index].copy(completeFlag = isComplete)
                currentList[index] = item
                _todoItems.value = currentList
            }
        }
    }

    private fun initialTodoItems(): MutableList<ToDoItem> {
        return mutableListOf(
            ToDoItem(
                id = 1,
                text = "Купить молоко",
                priority = ListOfTaskStatus.LOW,
                deadlineComplete = "10"
            ),
            ToDoItem(id = 2, text = "Сходить в спортзал", priority = ListOfTaskStatus.HIGH),
            ToDoItem(id = 3, text = "Завершить проект", priority = ListOfTaskStatus.HIGH),
            ToDoItem(id = 4, text = "Сделать уборку", priority = ListOfTaskStatus.NORMAL),
            ToDoItem(id = 5, text = "Позвонить друзьям", priority = ListOfTaskStatus.LOW),
            ToDoItem(id = 6, text = "Записаться к врачу", priority = ListOfTaskStatus.HIGH),
            ToDoItem(id = 7, text = "Купить подарки", priority = ListOfTaskStatus.NORMAL),
            ToDoItem(id = 8, text = "Прочитать книгу", priority = ListOfTaskStatus.NORMAL),
            ToDoItem(id = 9, text = "Написать статью", priority = ListOfTaskStatus.LOW),
            ToDoItem(id = 10, text = "Подготовить презентацию", priority = ListOfTaskStatus.HIGH)
        )
    }
}
