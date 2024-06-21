package com.example.todolistyandex.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.data.repository.ToDoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: ToDoItemsRepository) : ViewModel() {

    private var _taskItem = MutableLiveData<ToDoItem>()
    val taskItem: LiveData<ToDoItem> = _taskItem

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
            repository.addOrUpdateTodoItem(task)
        }
    }

    fun deleteTodoItem() = viewModelScope.launch(Dispatchers.Main) {
        _taskItem.value?.let { task ->
            repository.deleteTodoItem(task.id)
        }
    }

    fun getTaskById(taskId: Int) = repository.getTodoItemById(taskId).asLiveData(Dispatchers.IO)
}

class TaskViewModelFactory(private val repository: ToDoItemsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
