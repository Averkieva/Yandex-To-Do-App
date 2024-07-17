package com.example.todolistyandex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.data.repository.ToDoItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for managing the state and data related to the list of tasks,
 * providing methods to fetch, filter, and update tasks, and handle completed task counts.
 */

@HiltViewModel
open class ListOfTaskViewModel @Inject constructor(
    private val repository: ToDoItemsRepository
) : ViewModel() {

    private val _showCompletedTasks = MutableStateFlow(true)
    val showCompletedTasks: StateFlow<Boolean> = _showCompletedTasks.asStateFlow()

    val fetchError: StateFlow<String?> = repository.fetchError

    open val tasks: Flow<List<ToDoItem>> = combine(
        repository.todoItems,
        showCompletedTasks
    ) { tasks, showCompleted ->
        val filteredTasks = if (showCompleted) tasks else tasks.filter { !it.completeFlag }
        filteredTasks
    }

    init {
        viewModelScope.launch {
            repository.fetchTodoItems()
        }
    }

    fun retryFetch() {
        viewModelScope.launch {
            repository.retryFetchTodoItems()
        }
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


