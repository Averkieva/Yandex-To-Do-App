package com.example.todolistyandex.data.network.request

import com.example.todolistyandex.data.model.RemoteTodoItem

/**
 * Data class representing a request to add or update a remote ToDo item,
 * containing the RemoteTodoItem object to be sent to the server.
 */

data class TodoItemRequest(
    val element: RemoteTodoItem
)