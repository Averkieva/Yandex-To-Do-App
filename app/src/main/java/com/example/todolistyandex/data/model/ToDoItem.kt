package com.example.todolistyandex.data.model

import java.util.UUID

/**
 * Data class representing a ToDoItem with properties for task details,
 * priority, deadlines, and status flags.
 */

data class ToDoItem(
    var id: Int = 0,
    val uuid: String = UUID.randomUUID().toString(),
    var text: String = "",
    var priority: ListOfTaskStatus = ListOfTaskStatus.NORMAL,
    var deadlineComplete: String? = "",
    var completeFlag: Boolean = false,
    var creationDate: String = "",
    var changeDate: String = ""
)