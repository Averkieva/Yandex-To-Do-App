package com.example.todolistyandex.data.model

data class ToDoItem(
    var id: Int = 0,
    var text: String = "",
    var priority: ListOfTaskStatus = ListOfTaskStatus.NORMAL,
    var deadlineComplete: String? = "",
    var completeFlag: Boolean = false,
    var creationDate: String = "",
    var changeDate: String = ""
)