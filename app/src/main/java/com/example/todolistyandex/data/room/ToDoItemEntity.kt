package com.example.todolistyandex.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolistyandex.data.model.ListOfTaskStatus
import java.util.UUID

@Entity(tableName = "todo_items")
data class ToDoItemEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var uuid: String = UUID.randomUUID().toString(),
    var text: String = "",
    var priority: ListOfTaskStatus = ListOfTaskStatus.NORMAL,
    var deadlineComplete: String? = "",
    var completeFlag: Boolean = false,
    var creationDate: String = "",
    var changeDate: String = ""
) {
}


