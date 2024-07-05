package com.example.todolistyandex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolistyandex.data.repository.ToDoItemsRepository

/**
 * Factory class for creating ViewModel instances, initializing TaskViewModel and
 * ListOfTaskViewModel with the provided ToDoItemsRepository.
 */

class ToDoViewModelFactory(private val repository: ToDoItemsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TaskViewModel::class.java) -> {
                TaskViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ListOfTaskViewModel::class.java) -> {
                ListOfTaskViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
