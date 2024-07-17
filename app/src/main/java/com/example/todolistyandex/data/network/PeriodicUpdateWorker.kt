package com.example.todolistyandex.data.network

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todolistyandex.data.repository.ToDoItemsRepository
import com.example.todolistyandex.ui.activity.MyApplication

/**
 * CoroutineWorker class for performing periodic updates of the ToDoList,
 * interacting with the ToDoItemsRepository to update tasks in the background.
 */

class PeriodicUpdateWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val repository = (applicationContext as MyApplication).repository

        return try {
            repository.updateTodoList()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}




