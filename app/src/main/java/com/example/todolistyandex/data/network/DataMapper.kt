package com.example.todolistyandex.data.network

import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.data.model.RemoteTodoItem
import com.example.todolistyandex.data.model.ToDoItem
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Object for mapping between RemoteTodoItem and ToDoItem, converting data
 * between domain and remote representations.
 */

object DataMapper {

    fun toDomain(remoteItem: RemoteTodoItem): ToDoItem {
        return ToDoItem(
            id = remoteItem.id.hashCode(),
            uuid = remoteItem.id,
            text = remoteItem.text,
            priority = when (remoteItem.importance) {
                "low" -> ListOfTaskStatus.LOW
                "important" -> ListOfTaskStatus.HIGH
                else -> ListOfTaskStatus.NORMAL
            },
            deadlineComplete = remoteItem.deadline?.let {
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(it * 1000)
            } ?: "",
            completeFlag = remoteItem.done,
            creationDate = SimpleDateFormat(
                "dd.MM.yyyy",
                Locale.getDefault()
            ).format(remoteItem.created_at * 1000),
            changeDate = SimpleDateFormat(
                "dd.MM.yyyy",
                Locale.getDefault()
            ).format(remoteItem.changed_at * 1000)
        )
    }

    fun toRemote(domainItem: ToDoItem): RemoteTodoItem {
        return RemoteTodoItem(
            id = domainItem.uuid,
            text = domainItem.text,
            importance = when (domainItem.priority) {
                ListOfTaskStatus.LOW -> "low"
                ListOfTaskStatus.HIGH -> "important"
                else -> "basic"
            },
            deadline = domainItem.deadlineComplete.toUnixTimestamp(),
            done = domainItem.completeFlag,
            color = null,
            created_at = domainItem.creationDate.toUnixTimestamp() ?: 0L,
            changed_at = domainItem.changeDate.toUnixTimestamp() ?: 0L,
            last_updated_by = "1"
        )
    }

    private fun String?.toUnixTimestamp(): Long? {
        return this?.let {
            try {
                val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val date = format.parse(it)
                date?.time?.div(1000)
            } catch (e: Exception) {
                null
            }
        }
    }
}
