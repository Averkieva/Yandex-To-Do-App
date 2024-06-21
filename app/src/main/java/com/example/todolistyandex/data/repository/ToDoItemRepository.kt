package com.example.todolistyandex.data.repository

import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.data.model.ToDoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class ToDoItemsRepository {

    private val _todoItems = MutableStateFlow(initialTodoItems())
    private var currentMaxId = _todoItems.value.maxOfOrNull { it.id } ?: 0

    val todoItems: StateFlow<List<ToDoItem>>
        get() = _todoItems

    fun getTodoItemById(id: Int): Flow<ToDoItem?> {
        return todoItems.map { items -> items.find { it.id == id } }
    }

    @Synchronized
    fun addOrUpdateTodoItem(item: ToDoItem): ToDoItem {
        val currentList = _todoItems.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == item.id }

        if (index >= 0) {
            currentList[index] = item
        } else {
            val newItem = item.copy(id = ++currentMaxId)
            currentList.add(newItem)
            _todoItems.value = currentList
            return newItem
        }

        _todoItems.value = currentList
        return item
    }


    fun updateTodoItem(item: ToDoItem) {
        val currentList = _todoItems.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == item.id }
        if (index >= 0) {
            currentList[index] = item
            _todoItems.value = currentList
        }
    }

    @Synchronized
    fun deleteTodoItem(id: Int) {
        val currentList = _todoItems.value.toMutableList()
        currentList.removeAll { it.id == id }
        _todoItems.value = currentList
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
