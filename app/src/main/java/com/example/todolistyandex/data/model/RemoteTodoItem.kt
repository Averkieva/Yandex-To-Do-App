package com.example.todolistyandex.data.model

/**
 * Data class representing a remote TodoItem with properties for task details,
 * metadata, and synchronization information.
 */

data class RemoteTodoItem(
    val id: String,
    val text: String,
    val importance: String,
    val deadline: Long?,
    val done: Boolean,
    val color: String?,
    val created_at: Long,
    val changed_at: Long,
    val last_updated_by: String
)