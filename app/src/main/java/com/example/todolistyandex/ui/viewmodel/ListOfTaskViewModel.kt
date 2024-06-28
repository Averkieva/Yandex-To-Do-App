package com.example.todolistyandex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.data.repository.ToDoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class ListOfTaskViewModel(private val repository: ToDoItemsRepository) : ViewModel() {

    private val _showCompletedTasks = MutableStateFlow(true)
    val showCompletedTasks: StateFlow<Boolean> = _showCompletedTasks.asStateFlow()

    open val tasks: Flow<List<ToDoItem>> = combine(
        repository.todoItems,
        showCompletedTasks
    ) { tasks, showCompleted ->
        val filteredTasks = if (showCompleted) tasks else tasks.filter { !it.completeFlag }
        filteredTasks
    }

    val completedTaskCount: Flow<Int> = tasks.map { tasks ->
        tasks.count { it.completeFlag }
    }.distinctUntilChanged()

    fun toggleShowCompletedTasks() {
        _showCompletedTasks.value = !_showCompletedTasks.value
    }

    open fun updateTaskCompletion(taskId: Int, isComplete: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTaskCompletion(taskId, isComplete)
        }
    }
}

class ListOfTaskViewModelFactory(private val repository: ToDoItemsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListOfTaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListOfTaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


