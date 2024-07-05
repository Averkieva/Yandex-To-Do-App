package com.example.todolistyandex.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.data.repository.ToDoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel class for managing the state and data of a single ToDoItem,
 * providing methods to set task properties, save, delete, and retrieve tasks
 * from the repository, and handle fetch errors.
 */

open class TaskViewModel(private val repository: ToDoItemsRepository) : ViewModel() {

    private var _taskItem = MutableLiveData<ToDoItem>()
    val taskItem: LiveData<ToDoItem> = _taskItem

    val fetchError: StateFlow<String?> = repository.fetchError

    fun setTask(task: ToDoItem) {
        _taskItem.value = task
    }

    fun setPriority(priority: ListOfTaskStatus) {
        _taskItem.value?.priority = priority
        _taskItem.value = _taskItem.value
    }

    fun setText(text: String?) {
        if (text != null) {
            _taskItem.value?.text = text.trim()
            _taskItem.value = _taskItem.value
        }
    }

    fun setDeadline(deadline: String?) {
        _taskItem.value!!.deadlineComplete = deadline
        _taskItem.value = _taskItem.value
    }

    fun saveToDoItem() = viewModelScope.launch(Dispatchers.IO) {
        _taskItem.value?.let { task ->
            if (task.id == 0) {
                repository.addOrUpdateTodoItem(task)
            } else {
                repository.updateTodoItem(task)
            }
        }
    }

    fun deleteTodoItem() = viewModelScope.launch(Dispatchers.IO) {
        _taskItem.value?.let { task ->
            repository.deleteTodoItem(task.id)
        }
    }

    open fun getTaskById(taskId: Int) =
        repository.getTodoItemById(taskId).asLiveData(Dispatchers.IO)

    fun clearFetchError() {
        viewModelScope.launch {
            repository.clearFetchError()
        }
    }
}