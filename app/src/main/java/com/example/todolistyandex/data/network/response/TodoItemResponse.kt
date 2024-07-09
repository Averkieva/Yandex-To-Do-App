package com.example.todolistyandex.data.network.response

import com.example.todolistyandex.data.model.RemoteTodoItem

/**
 * Data class representing a response from the server for a ToDoItem request,
 * containing the status, the RemoteTodoItem object, and the revision number.
 */

data class TodoItemResponse(
    val status: String,
    val element: RemoteTodoItem,
    val revision: Int
)