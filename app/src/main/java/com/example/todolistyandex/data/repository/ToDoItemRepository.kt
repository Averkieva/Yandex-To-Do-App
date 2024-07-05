package com.example.todolistyandex.data.repository

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todolistyandex.data.network.DataMapper
import com.example.todolistyandex.data.network.ErrorHandler
import com.example.todolistyandex.data.network.PeriodicUpdateWorker
import com.example.todolistyandex.data.network.RetrofitClient
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.data.network.request.TodoItemRequest
import com.example.todolistyandex.data.network.response.TodoItemResponse
import com.example.todolistyandex.data.network.response.TodoListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID
import java.util.concurrent.TimeUnit

/**
 * Repository class for managing ToDoItems, providing methods to fetch, add, update,
 * delete, and periodically update tasks, and handling error responses and revision
 * synchronization with a remote server.
 */

open class ToDoItemsRepository {

    private val _todoItems = MutableStateFlow<List<ToDoItem>>(emptyList())
    val todoItems: StateFlow<List<ToDoItem>> get() = _todoItems

    private var revision = 0

    private val _fetchError = MutableStateFlow<String?>(null)
    val fetchError: StateFlow<String?> = _fetchError

    suspend fun fetchTodoItems() {
        try {
            val response = RetrofitClient.api.getTodoList()
            if (response.isSuccessful) {
                handleSuccessfulFetch(response)
            } else {
                handleErrorResponse(response.code())
            }
        } catch (e: Exception) {
            _fetchError.value =
                "Ошибка соединения. Проверьте интернет-соединение и попробуйте снова."
        }
    }

    private fun handleSuccessfulFetch(response: Response<TodoListResponse>) {
        val remoteItems = response.body()?.list ?: emptyList()
        _todoItems.value = remoteItems.map { DataMapper.toDomain(it) }
        revision = response.body()?.revision ?: 0
        _fetchError.value = null
    }

    private fun handleErrorResponse(code: Int) {
        _fetchError.value = ErrorHandler.getErrorMessage(code)
    }

    suspend fun retryFetchTodoItems() {
        fetchTodoItems()
    }

    open suspend fun addOrUpdateTodoItem(item: ToDoItem): ToDoItem {
        return withContext(Dispatchers.IO) {
            val newItem = updateLocalList(item)
            withContext(NonCancellable) {
                try {
                    tryAddOrUpdateItem(newItem)
                } catch (e: Exception) {
                    _fetchError.value =
                        "Ошибка добавления задачи. Проверьте интернет-соединение и попробуйте снова."
                }
            }
            newItem
        }
    }

    private suspend fun updateLocalList(item: ToDoItem): ToDoItem {
        val currentList = _todoItems.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == item.id }
        return if (index >= 0) {
            currentList[index] = item
            item
        } else {
            val newItem = item.copy(id = getNextId(), uuid = UUID.randomUUID().toString())
            currentList.add(newItem)
            _todoItems.value = currentList
            newItem
        }
    }

    private fun getNextId(): Int {
        return _todoItems.value.maxOfOrNull { it.id }?.plus(1) ?: 1
    }

    private suspend fun tryAddOrUpdateItem(newItem: ToDoItem) {
        fetchCurrentRevision()
        val remoteItem = DataMapper.toRemote(newItem)
        val request = TodoItemRequest(remoteItem)
        val response: Response<TodoItemResponse>

        if (_todoItems.value.any { it.id == newItem.id }) {
            response = RetrofitClient.api.updateTodoItem(newItem.uuid, revision, request)
        } else {
            response = RetrofitClient.api.addTodoItem(revision, request)
        }

        if (response.isSuccessful) {
            revision = response.body()?.revision ?: revision
        } else {
            handleAddOrUpdateError(response, request)
        }
    }


    private suspend fun handleAddOrUpdateError(
        response: Response<TodoItemResponse>,
        request: TodoItemRequest
    ) {
        delay(500)
        fetchCurrentRevision()
        val retryResponse = RetrofitClient.api.addTodoItem(revision, request)
        if (!retryResponse.isSuccessful) {
        }
    }

    private suspend fun fetchCurrentRevision() {
        val response = RetrofitClient.api.getTodoList()
        if (response.isSuccessful) {
            revision = response.body()?.revision ?: 0
        } else {
        }
    }

    suspend fun updateTodoList(): List<ToDoItem>? {
        return withContext(Dispatchers.IO) {
            val remoteItems = _todoItems.value.map { DataMapper.toRemote(it) }
            val response = RetrofitClient.api.updateTodoList(revision, remoteItems)
            if (response.isSuccessful) {
                val updatedItems = response.body()?.list ?: emptyList()
                _todoItems.value = updatedItems.map { DataMapper.toDomain(it) }
                revision = response.body()?.revision ?: 0
                _todoItems.value
            } else {
                handleErrorResponse(response.code())
                null
            }
        }
    }

    fun startPeriodicUpdate(context: Context) {
        val periodicWorkRequest =
            PeriodicWorkRequestBuilder<PeriodicUpdateWorker>(8, TimeUnit.HOURS).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "PeriodicUpdateWork",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

    suspend fun updateTodoItem(item: ToDoItem): ToDoItem? {
        return withContext(Dispatchers.IO) {
            val currentList = _todoItems.value.toMutableList()
            val index = currentList.indexOfFirst { it.id == item.id }
            if (index >= 0) {
                val updatedItem = item.copy(
                    changeDate = SimpleDateFormat(
                        "dd.MM.yyyy",
                        Locale.getDefault()
                    ).format(System.currentTimeMillis())
                )
                currentList[index] = updatedItem
                _todoItems.value = currentList
                try {
                    tryAddOrUpdateItem(updatedItem)
                } catch (e: Exception) {
                    _fetchError.value =
                        "Ошибка редактирования задачи. Проверьте интернет-соединение и попробуйте снова."
                }
                updatedItem
            } else {
                null
            }
        }
    }

    suspend fun deleteTodoItem(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val currentList = _todoItems.value.toMutableList()
            val item = currentList.find { it.id == id }
            if (item != null) {
                currentList.remove(item)
                _todoItems.value = currentList
                try {
                    tryDeleteItem(item)
                } catch (e: Exception) {
                    _fetchError.value =
                        "Ошибка удаления задачи. Проверьте интернет-соединение и попробуйте снова."
                }
                true
            } else {
                false
            }
        }
    }

    private suspend fun tryDeleteItem(item: ToDoItem) {
        withContext(NonCancellable) {
            fetchCurrentRevision()
            val response = RetrofitClient.api.deleteTodoItem(item.uuid, revision)
            if (!response.isSuccessful) {
                _todoItems.value = _todoItems.value.toMutableList().apply { add(item) }
            }
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

    open fun getTodoItemById(id: Int): Flow<ToDoItem?> {
        return todoItems.map { items -> items.find { it.id == id } }
    }

    fun clearFetchError() {
        _fetchError.value = null
    }
}