package com.example.todolistyandex.ui.activity

import android.app.Application
import com.example.todolistyandex.data.repository.ToDoItemsRepository

/**
 * Application class for initializing the ToDoItemsRepository and starting periodic
 * updates of the ToDoList when the application is created.
 */

class MyApplication : Application() {
    val repository: ToDoItemsRepository by lazy { ToDoItemsRepository() }

    override fun onCreate() {
        super.onCreate()
        repository.startPeriodicUpdate(this)
    }
}