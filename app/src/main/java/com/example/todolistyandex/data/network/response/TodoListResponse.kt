package com.example.todolistyandex.data.network.response

import com.example.todolistyandex.data.model.RemoteTodoItem

/**
 * Data class representing a response from the server for a request to retrieve
 * a list of ToDoItems, containing the status, a list of RemoteTodoItem objects,
 * and the revision number.
 */

data class TodoListResponse(
    val status: String,
    val list: List<RemoteTodoItem>,
    val revision: Int
)